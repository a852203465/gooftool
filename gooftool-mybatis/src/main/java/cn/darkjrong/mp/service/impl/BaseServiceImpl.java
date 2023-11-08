package cn.darkjrong.mp.service.impl;

import cn.darkjrong.core.lang.constants.NumberConstant;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.darkjrong.mp.service.BaseService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import cn.darkjrong.mp.utils.PageableUtils;
import cn.darkjrong.mp.utils.PropertyUtils;
import cn.darkjrong.pager.dto.PageDTO;
import cn.darkjrong.pager.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 业务层 （父级）接口 实现类
 * @param <E> 实体类
 * @param <T> 目标对象
 * @param <M> 实体类对应Mapper
 * @param <S> 源对象
 * @author Rong.Jia
 * @date 2020/05/21 18:59
 */
@Slf4j
@SuppressWarnings("ALL")
public class BaseServiceImpl<M extends BaseMapper<E>, E, S, T> extends ServiceImpl<M, E> implements BaseService<E, S, T> {

    protected Class<T> targetClass = currentTargetClass();
    protected Class<T> currentTargetClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseServiceImpl.class, 3);
    }

    @Override
    public PageVO<T> page(PageDTO pageDTO) {
        if (pageDTO.getCurrentPage() > NumberConstant.ZERO) {
            PageableUtils.basicPage(pageDTO);
        }
        PageInfo<S> pageInfo = new PageInfo<>(this.queryList(pageDTO));
        return PropertyUtils.copyProperties(pageInfo, this.objectConversion(pageInfo.getList()));
    }

    @Deprecated
    @Override
    public List<S> queryList(PageDTO pageDTO) {
        return Collections.emptyList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean delete(Serializable id) {
        if (ObjectUtil.isNotEmpty(id)) return this.removeById(id);
        return Boolean.FALSE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<? extends Serializable> ids) {
        if (CollectionUtil.isNotEmpty(ids)) {
            ids.forEach(this::delete);
        }
    }

    @Override
    public T queryById(Serializable id) {
        E e = this.getById(id);
        if (ObjectUtil.isNotNull(e)) {
            return BeanUtil.copyProperties(e, currentTargetClass());
        }
        return null;
    }

    @Override
    public T objectConversion(S s) {
        if (s == null) return null;
        return BeanUtil.copyProperties(s, this.targetClass);
    }

    @Override
    public List<T> objectConversion(List<S> sList) {
        if (CollectionUtil.isNotEmpty(sList)) {
            List<T> tList = new ArrayList<>();
            sList.forEach(a -> Optional.ofNullable(this.objectConversion(a)).ifPresent(tList::add));
            return tList;
        }
        return Collections.emptyList();
    }

    @Override
    public T objectConversion(S s, String... ignoreProperties) {
        if (s == null) return null;
        return BeanUtil.copyProperties(s, this.targetClass, ignoreProperties);
    }

    @Override
    public List<T> objectConversion(List<S> sList, String... ignoreProperties) {
        if (CollectionUtil.isNotEmpty(sList)) {
            List<T> toList = CollectionUtil.newArrayList();
            sList.forEach(a -> Optional.ofNullable(this.objectConversion(a, ignoreProperties)).ifPresent(toList::add));
            return toList;
        }
        return Collections.emptyList();
    }

    @Override
    public List<T> objectConversion(List<S> sList, CopyOptions copyOptions) {
        if (CollectionUtil.isNotEmpty(sList)) {
            List<T> toList = CollectionUtil.newArrayList();
            sList.forEach(a -> Optional.ofNullable(this.objectConversion(a, copyOptions)).ifPresent(toList::add));
            return toList;
        }
        return Collections.emptyList();
    }

    @Override
    public T objectConversion(S s, CopyOptions copyOptions) {
        if (s == null) return null;
        T t = ReflectUtil.newInstanceIfPossible(this.targetClass);
        BeanUtil.copyProperties(s, t, copyOptions);
        return t;
    }
}
