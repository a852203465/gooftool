package cn.darkjrong.jpa.service;

import cn.darkjrong.pager.dto.PageDTO;
import cn.darkjrong.pager.dto.SortDTO;
import cn.darkjrong.pager.vo.PageVO;
import cn.hutool.core.bean.copier.CopyOptions;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 *  抽取service层通用方法
 * @param <T>  目标对象
 * @param <E>  实体类
 * @author Rong.Jia
 * @date 2019/01/14 17:00
 */
public interface BaseService<E, T> {

    /**
     * 分页查询
     *
     * @param pageDTO 页面DTO
     * @return {@link PageVO}<{@link T}>
     */
    PageVO<T> page(PageDTO pageDTO);

    /**
     * 分页查询
     *
     * @param pageDTO 页面DTO
     * @return {@link PageVO}<{@link T}>
     * @param spec 查询条件
     */
    PageVO<T> page(Specification<E> spec, PageDTO pageDTO);

    /**
     * 条件查询集合
     *
     * @return {@link List}<{@link T}>
     * @param spec 查询条件
     */
    List<T> findAll(Specification<E> spec);

    /**
     * 条件查询集合
     * @param sortDTO 排序
     * @return {@link List}<{@link T}>
     * @param spec 查询条件
     */
    List<T> findAll(Specification<E> spec, SortDTO sortDTO);

    /**
     * 条件查询一条
     *
     * @param spec 查询条件
     * @return {@link T}
     */
    T findOne(Specification<E> spec);

    /**
     * 根据id 获取信息
     * @param id 信息唯一标识
     * @return E 获取的信息
     */
    T findById(Long id);

    /**
     * 获取所有的信息
     * @return 将获取的信息封装到List中 返回
     */
    List<T> findAll();

    /**
     * 删除指定的信息
     * @param entity 实体类信息
     */
    void delete(E entity);

    /**
     *  根据id 删除信息
     * @param id 唯一标识
     */
    void deleteById(Long id);

    /**
     *  添加一个新的信息
     * @param entity 信息对象
     * @return E 新增的对象信息
     */
    T insetNew(E entity);

    /**
     * 添加多个新的信息
     * @param entity 信息对象List
     * @return  List<T> 批量增加的对象列表
     */
    List<T> insetNewAll(List<E> entity);

    /**
     * 修改信息
     * @param entity 信息对象
     * @return E 修改成功的信息对象
     */
    T modify(E entity);

    /**
     * 多选删除
     * @param entities 待删除对象集合
     */
    void deleteInBatch(List<E> entities);

    /**
     * 删除所有
     */
    void deleteAll();

    /**
     *  对象转换
     * @param e  源对象
     * @return 目标对象
     */
    T objectConversion(E e);

    /**
     *  对象转换
     * @param sList  源对象集合
     * @return 目标对象集合
     */
    List<T> objectConversion(List<E> sList);

    /**
     *  对象转换
     * @param e  源对象
     * @param ignoreProperties 不拷贝的的属性列表
     * @return 目标对象
     */
    T objectConversion(E e, String... ignoreProperties);

    /**
     *  对象转换
     * @param sList  源对象集合
     * @param ignoreProperties 不拷贝的的属性列表
     * @return 目标对象集合
     */
    List<T> objectConversion(List<E> sList, String... ignoreProperties);

    /**
     * 对象转换
     *
     * @param sList       源对象集合
     * @param copyOptions 复制选项
     * @return {@link List}<{@link T}> 目标对象集合
     */
    List<T> objectConversion(List<E> sList, CopyOptions copyOptions);

    /**
     * 对象转换
     *
     * @param copyOptions 复制选项
     * @param e           源对象
     * @return {@link T} 目标对象
     */
    T objectConversion(E e, CopyOptions copyOptions);



}
