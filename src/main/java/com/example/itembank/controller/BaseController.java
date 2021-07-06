package com.example.itembank.controller;

import com.example.itembank.base.ifs.ControllerInterface;
import com.example.itembank.base.ifs.CrudInterface;
import com.example.itembank.model.network.Header;
import com.example.itembank.service.BaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Component
public abstract class BaseController<Req, Res, Entity> implements ControllerInterface<Req, Res> {

    @Autowired(required = false)
    protected BaseService<Req, Res, Entity> baseService;

    @Override
    @ApiOperation(value = "데이터 생성", notes = "도메인 데이터를 생성합니다.")
    @PostMapping("")
    public Header<Res> create(@RequestBody Req request) {
        return baseService.create(request);
    }

    @Override
    @ApiOperation(value = "데이터 상세조회", notes = "도메인 데이터를 상세조회 합니다.")
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable Long id) {
        return baseService.read(id);
    }

    @Override
    @ApiOperation(value = "데이터 수정", notes = "도메인 데이터를 수정합니다.")
    @PutMapping("")
    public Header<Res> update(@RequestBody Req request) {
        return baseService.update(request);
    }

    @Override
    @ApiOperation(value = "데이터 삭제", notes = "도메인 데이터를 삭제합니다.")
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return baseService.delete(id);
    }

    @Override
    @ApiOperation(value = "전체 조회", notes = "도메인 데이터를 전체조회 합니다.")
    @GetMapping("")
    public Header<List<Res>> search(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10) Pageable pageable){
        return baseService.search(pageable);
    }
}