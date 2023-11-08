package cn.darkjrong.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *  抽取service层通用方法
 * @param <T>
 * @author Rong.Jia
 * @date 2019/01/14 17:00
 */
public interface BaseService<T> {

    /**
     * 根据id 获取信息
     * @param id 信息唯一标识
     * @return T 获取的信息
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
    void delete(T entity);

    /**
     *  根据id 删除信息
     * @param id 唯一标识
     */
    void deleteById(Long id);

    /**
     *  添加一个新的信息
     * @param entity 信息对象
     * @return T 新增的对象信息
     */
    T insetNew(T entity);

    /**
     * 添加多个新的信息
     * @param entity 信息对象List
     * @return  List<T> 批量增加的对象列表
     */
    List<T> insetNewAll(List<T> entity);

    /**
     * 修改信息
     * @param entity 信息对象
     * @return T 修改成功的信息对象
     */
    T modify(T entity);

    /**
     * 分页
     * @param pageable 分页参数
     * @return 分页数据
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 多选删除
     * @param entities 待删除对象集合
     */
    void deleteInBatch(List<T> entities);

    /**
     * 删除所有
     */
    void deleteAll();


}
