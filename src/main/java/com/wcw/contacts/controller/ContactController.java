package com.wcw.contacts.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcw.contacts.entity.Contact;
import com.wcw.contacts.service.ContactService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 王成伍
 */
@RestController
@RequestMapping(value = "/contact")
@Api(tags = "ContactController", description = "ContactController | 通讯录")
public class ContactController {
    /**
     * 定义一个全局的记录器，通过LoggerFactory获取
     */
    private final static Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @ApiOperation(value="新增联系人", notes="add a contact")
    @ApiImplicitParam(name = "contact", value = "联系人数据模型", required = true, dataType = "Contact")
    @PostMapping(value = "/add", produces = {"text/plain;charset=UTF-8"})
    public String addContact(@RequestBody Contact contact){
        int affectRows=contactService.addContact(contact);
        String strRet=affectRows > 0 ? "Success" : "Failed";
        logger.info("添加联系人{}",strRet);
        return strRet;
    }

    @ApiOperation(value="更新联系人", notes="update a contact")
    @ApiImplicitParam(name = "contact", value = "联系人数据模型", required = true, dataType = "Contact")
    @PutMapping(value = "/update", produces = {"text/plain;charset=UTF-8"})
    public String updateContact(@RequestBody Contact contact){
        int affectRows=contactService.updateContact(contact);
        String strRet=affectRows > 0 ? "Success" : "Failed";
        logger.info("更新联系人{}",strRet);
        return strRet;
    }

    @ApiOperation(value="删除联系人", notes="delete a contact")
    @ApiParam(name = "contactId", value = "联系人id", required = true)
    @PostMapping(value = "/delete", produces = {"text/plain;charset=UTF-8"})
    public String deleteContact(@RequestParam("contactId") int contactId){
        int affectRows=contactService.deleteContactById(contactId);
        String strRet=affectRows > 0 ? "Success" : "Failed";
        logger.info("删除联系人{}",strRet);
        return strRet;
    }

    @ApiOperation(value="分页查询联系人", notes="find some contacts by page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true,dataType = "int",paramType = "query")
    })
    @GetMapping(value = "/pagingAll", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<Contact>> findAllContactByPage(@RequestParam(name="pageNum",defaultValue = "1") int pageNum,
                                      @RequestParam(name="pageSize",defaultValue = "1") int pageSize){
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Contact> info = new PageInfo<>();
        List<Contact> contacts = contactService.findAllContact();
        info.setList(contacts);
        return ResponseEntity.ok(info.getList());
    }

    @ApiOperation(value="查询所有联系人", notes="query all contacts")
    @GetMapping(value = "/all", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<Contact>> findAllContact(){
        List<Contact> contacts = contactService.findAllContact();
        return ResponseEntity.ok(contacts);
    }


    @ApiOperation(value="根据联系人id获取单个联系人", notes="query a contact by id")
    @ApiParam(name = "contactId", value = "联系人id", required = true)
    @GetMapping(value = "/getById", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Contact> GetContactByContactId(@RequestParam("contactId") int contactId){
        Contact contact = contactService.getContactById(contactId);
        return ResponseEntity.ok(contact);
    }


}
