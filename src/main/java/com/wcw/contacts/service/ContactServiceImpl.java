package com.wcw.contacts.service;

import com.wcw.contacts.entity.Contact;
import com.wcw.contacts.mapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王成伍
 */
@Service(value = "contactService")
public class ContactServiceImpl implements ContactService {
    /**
     * 这里会报错，但是并不会影响
     */
    @Autowired
    private ContactMapper contactMapper;

    @Override
    public int addContact(Contact contact) {
        return contactMapper.insertSelective(contact);
    }

    @Override
    public int updateContact(Contact contact) {
        return contactMapper.updateByPrimaryKeySelective(contact);
    }

    @Override
    public int deleteContactById(int contactId) {
        return contactMapper.deleteByPrimaryKey(contactId);
    }

    @Override
    public Contact getContactById(int contactId) {
        return contactMapper.selectByPrimaryKey(contactId);
    }

    /**
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    @Override
    public List<Contact> findAllContact() {
        return contactMapper.selectAllContacts();
    }
}