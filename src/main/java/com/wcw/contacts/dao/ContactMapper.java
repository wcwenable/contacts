package com.wcw.contacts.dao;

import com.wcw.contacts.entity.Contact;

import java.util.List;

/**
 * @author 王成伍
 */
public interface ContactMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Contact record);

    int insertSelective(Contact record);

    Contact selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Contact record);

    int updateByPrimaryKey(Contact record);

    /**
     * 返回所有联系人
     */
    List<Contact> selectAllContacts();
}