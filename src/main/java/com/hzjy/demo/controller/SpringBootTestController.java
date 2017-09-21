package com.hzjy.demo.controller;

import com.hzjy.demo.mapper.PersonMapper;
import com.hzjy.demo.pojo.Person;
import com.hzjy.demo.pojo.PersonExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/mybatis")
public class SpringBootTestController {

   @Autowired
   private PersonMapper personMapper;

    /**
     * 根据id查询
     */
    @RequestMapping("/selectAll")
    @ResponseBody
    public List<Person> getAll(){
        PersonExample example = new PersonExample();
        PersonExample.Criteria criteria = example.createCriteria();
        criteria.andIdBetween(0,4);
        List<Person> list = personMapper.selectByExample(example);
        return list;
    }

    /**
     * 插入数据
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Person insert(){
        //System.out.println("xxxxxxxxxxxxxxxxxxx");
        Person person = new Person();
        person.setId(3);
        person.setName("Json");
        person.setSex("男");
        person.setAge(16);
        personMapper.insert(person);
        return person;
    }

    /**
     * 删除数据
     */
    @RequestMapping("/delete")
    @ResponseBody
    public int delete(){
        PersonExample example = new PersonExample();
        PersonExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(3);
        int i = personMapper.deleteByExample(example);
        return i;
    }
}
