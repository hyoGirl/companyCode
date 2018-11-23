package com.mybatisPlus.quickStart.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mybatisPlus.quickStart.entity.Person;

import java.util.List;

public interface PersonService extends IService<Person>{

    void  insertPerson(Person person);


    int  deletePersonById(long id);


    void  updatedPerson(Person person, Wrapper updateWrapper);


    List<Person> findAllPerson();


    Person findPersonByCondition(Wrapper<Person> wrapper);


    List<Person> findPersonPage(Page page, EntityWrapper<Person> entityWrapper);

}
