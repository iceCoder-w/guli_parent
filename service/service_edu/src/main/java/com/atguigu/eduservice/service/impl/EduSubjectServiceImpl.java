package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 王冰炜
 * @since 2021-04-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            // 文件输入流
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }

    }

    // 获得课程分类列表（树形实现）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        // 查询一级
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // 查询二级
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // 创建一个终极List集合，存储最终封装好的数据并返回给前端
        List<OneSubject> finalSubjectList = new ArrayList<>();

        // 封装一级
        // 把eduSubject里面值获取出来，放到OneSubject对象里面
        // 多个OneSubject放到finalSubjectList里面
        for (EduSubject eduSubject : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject, oneSubject);
            // 封装二级
            // 在一级分类循环遍历查询所有的二级分类
            // 创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (EduSubject tSubject : twoSubjectList) {
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
            finalSubjectList.add(oneSubject);
        }

        return finalSubjectList;
    }
}
