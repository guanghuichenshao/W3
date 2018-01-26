package com.zyc.dao;

import com.zyc.po.Article;
import com.zyc.po.Bbsuser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Administrator on 2018/1/11 0011.
 */
public interface IBBSUserDao extends CrudRepository<Bbsuser,Integer>{
    @Query("select c from Bbsuser c where c.username=:u and c.password=:p")
    public Bbsuser login(@Param("u") String username, @Param("p") String password);
    @Query("select c from Bbsuser c where c.userid=:id")
    public Bbsuser pic(@Param("id") Integer userid);
    @Modifying
    @Query("update Bbsuser set pagenum=:num where userid=:id")
    public int setPagenum(@Param("id") Integer userid,@Param("num") Integer pagenum);
    @Query("select pagenum from Bbsuser c where c.userid=:id")
    public int getPagenum(@Param("id") Integer userid);
    public Bbsuser  save(Bbsuser user);

}
