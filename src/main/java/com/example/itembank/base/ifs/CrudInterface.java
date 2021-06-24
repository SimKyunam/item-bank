package com.example.itembank.base.ifs;

import com.example.itembank.model.network.Header;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CrudInterface<Req, Res> {

    Header<Res> create(Req request);

    Header<Res> read(Long id);

    Header<Res> update(Req request);

    Header delete(Long id);

    Header<List<Res>> search(Pageable pageable);
}
