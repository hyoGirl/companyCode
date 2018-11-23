package com.mybatisPlus.quickStart.Service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mybatisPlus.quickStart.Service.PersonService;
import com.mybatisPlus.quickStart.entity.Person;
import com.mybatisPlus.quickStart.mapper.PersonMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper,Person> implements PersonService {


    @Override
    public void insertPerson(Person person) {
        this.baseMapper.insert(person);
    }

    @Override
    public int deletePersonById(long id) {

        return this.baseMapper.deleteById(id);
    }

    @Override
    public void updatedPerson(Person person,Wrapper wrapper) {


//        Wrapper wrapper=new EntityWrapper();
        this.baseMapper.update(person,wrapper);

//        this.baseMapper.updateById(person);
    }

    @Override
    public List<Person> findAllPerson() {

        return this.baseMapper.selectList(null);
    }

    @Override
    public Person findPersonByCondition(Wrapper<Person> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    public List<Person> findPersonPage(Page page, EntityWrapper<Person> entityWrapper) {

        return this.baseMapper.selectPage(page, entityWrapper);
    }

}
