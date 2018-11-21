package com.wcw.contacts.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcw.contacts.entity.Contact;
import com.wcw.contacts.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 点觉CTM
 */
@RestController
@RequestMapping(value = "/contact")
public class ContactController {
    /**
     * 定义一个全局的记录器，通过LoggerFactory获取
     */
    private final static Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @PostMapping(value = "/add", produces = {"text/plain;charset=UTF-8"})
    public String addContact(@RequestBody Contact contact){
        int affectRows=contactService.addContact(contact);
        String strRet=affectRows > 0 ? "Success" : "Failed";
        logger.info("添加联系人{}",strRet);
        return strRet;
    }

    @PutMapping(value = "/update", produces = {"text/plain;charset=UTF-8"})
    public String updateContact(@RequestBody Contact contact){
        int affectRows=contactService.updateContact(contact);
        String strRet=affectRows > 0 ? "Success" : "Failed";
        logger.info("更新联系人{}",strRet);
        return strRet;
    }

    @PostMapping(value = "/delete", produces = {"text/plain;charset=UTF-8"})
    public String deleteContact(@RequestParam("contactId") int contactId){
        int affectRows=contactService.deleteContactById(contactId);
        String strRet=affectRows > 0 ? "Success" : "Failed";
        logger.info("删除联系人{}",strRet);
        return strRet;
    }

    @GetMapping(value = "/pagingAll", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<Contact>> findAllContactByPage(@RequestParam("pageNum") int pageNum,
                                      @RequestParam("pageSize") int pageSize){
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Contact> info = new PageInfo<>();
        List<Contact> contacts = contactService.findAllContact();
        info.setList(contacts);
        return ResponseEntity.ok(info.getList());
    }

    @GetMapping(value = "/all", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<Contact>> findAllContact(){
        List<Contact> contacts = contactService.findAllContact();
        return ResponseEntity.ok(contacts);
    }


    @GetMapping(value = "/getById", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Contact> GetContactByContactId(@RequestParam("contactId") int contactId){
        Contact contact = contactService.getContactById(contactId);
        return ResponseEntity.ok(contact);
    }


}
