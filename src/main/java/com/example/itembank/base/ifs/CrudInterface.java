package com.example.itembank.base.ifs;

import com.example.itembank.model.network.Header;

import java.awt.print.Pageable;
import java.util.List;

public interface CrudInterface<Req, Res> {

    Header<Res> create(Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> request);

    Header delete(Long id);

    Header<List<Res>> search(Pageable pageable);
}
