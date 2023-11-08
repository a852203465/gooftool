package cn.darkjrong.jpa.service.impl;

import cn.darkjrong.core.lang.constants.NumberConstant;
import cn.darkjrong.core.utils.ReflectionUtils;
import cn.darkjrong.jpa.repository.BaseRepository;
import cn.darkjrong.jpa.service.BaseService;
import cn.darkjrong.jpa.utils.PageableUtils;
import cn.darkjrong.jpa.utils.PropertyUtils;
import cn.darkjrong.jpa.utils.SortUtils;
import cn.darkjrong.pager.dto.PageDTO;
import cn.darkjrong.pager.dto.SortDTO;
import cn.darkjrong.pager.vo.PageVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * service层通用方法实现
 * @param <T>  目标对象
 * @param <E>  实体类
 * @author Rong.Jia
 * @date 2021/12/20
 */
@Slf4j
public class BaseServiceImpl<E, T> implements BaseService<E, T> {

    @Autowired
    protected BaseRepository<E> baseRepository;

    protected Class<T> targetClass = currentTargetClass();
    protected Class<T> currentTargetClass() {
        return (Class<T>) ReflectionUtils.getSuperClassGenericType(this.getClass(), BaseServiceImpl.class, 3);
    }

    @Override
    public PageVO<T> page(PageDTO pageDTO) {
        Page<E> page = null;
        if (pageDTO.getCurrentPage() > NumberConstant.ZERO) {
            Pageable pageable = PageableUtils.basicPage(pageDTO);
            page = baseRepository.findAll(pageable);
            return PropertyUtils.copyProperties(page, this.objectConversion(page.getContent()));
        }else {
            page = new PageImpl<>(baseRepository.findAll());
        }
        return PropertyUtils.copyProperties(page, this.objectConversion(page.getContent()));
    }

    @Override
    public PageVO<T> page(Specification<E> spec, PageDTO pageDTO) {
        Page<E> page = baseRepository.findAll(spec, PageableUtils.basicPage(pageDTO));
        return PropertyUtils.copyProperties(page, this.objectConversion(page.getContent()));
    }

    @Override
    public List<T> findAll(Specification<E> spec) {
        return this.objectConversion(baseRepository.findAll(spec));
    }

    @Override
    public List<T> findAll(Specification<E> spec, SortDTO sortDTO) {
        return this.objectConversion(baseRepository.findAll(spec, SortUtils.basicSort(sortDTO)));
    }

    @Override
    public T findOne(Specification<E> spec) {
        return this.objectConversion(baseRepository.findOne(spec).orElse(null));
    }

    @Override
    public T findById(Long id) {
        return this.objectConversion(baseRepository.getById(id));
    }

    @Override
    public List<T> findAll() {
        return this.objectConversion(baseRepository.findAll());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void delete(E entity) {
        baseRepository.delete(entity);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void deleteById(Long id) {
        if (ObjectUtil.isNotNull(baseRepository.getById(id))) {
            baseRepository.deleteById(id);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public T insetNew(E entity) {
        return this.objectConversion(baseRepository.save(entity));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public List<T> insetNewAll(List<E> entity) {
        return this.objectConversion(baseRepository.saveAll(entity));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public T modify(E entity) {
        return this.objectConversion(baseRepository.saveAndFlush(entity));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteInBatch(List<E> entities) {
        baseRepository.deleteAllInBatch(entities);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteAll() {
        baseRepository.deleteAll();
    }

    @Override
    public T objectConversion(E e) {
        if (e == null) return null;
        return BeanUtil.copyProperties(e, this.targetClass);
    }

    @Override
    public List<T> objectConversion(List<E> eList) {
        if (CollectionUtil.isNotEmpty(eList)) {
            List<T> tList = new ArrayList<>();
            eList.forEach(a -> Optional.ofNullable(this.objectConversion(a)).ifPresent(tList::add));
            return tList;
        }
        return Collections.emptyList();
    }

    @Override
    public T objectConversion(E e, String... ignoreProperties) {
        if (e == null) return null;
        return BeanUtil.copyProperties(e, this.targetClass, ignoreProperties);
    }

    @Override
    public List<T> objectConversion(List<E> eList, String... ignoreProperties) {
        if (CollectionUtil.isNotEmpty(eList)) {
            List<T> toList = CollectionUtil.newArrayList();
            eList.forEach(a -> Optional.ofNullable(this.objectConversion(a, ignoreProperties)).ifPresent(toList::add));
            return toList;
        }
        return Collections.emptyList();
    }

    @Override
    public List<T> objectConversion(List<E> eList, CopyOptions copyOptions) {
        if (CollectionUtil.isNotEmpty(eList)) {
            List<T> toList = CollectionUtil.newArrayList();
            eList.forEach(a -> Optional.ofNullable(this.objectConversion(a, copyOptions)).ifPresent(toList::add));
            return toList;
        }
        return Collections.emptyList();
    }

    @Override
    public T objectConversion(E e, CopyOptions copyOptions) {
        if (e == null) return null;
        T t = ReflectUtil.newInstanceIfPossible(this.targetClass);
        BeanUtil.copyProperties(e, t, copyOptions);
        return t;
    }
}
