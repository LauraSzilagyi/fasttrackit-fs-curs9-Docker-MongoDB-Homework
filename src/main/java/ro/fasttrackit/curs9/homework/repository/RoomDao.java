package ro.fasttrackit.curs9.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.curs9.homework.entity.Room;
import ro.fasttrackit.curs9.homework.filter.RoomFilter;

import java.util.List;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.support.PageableExecutionUtils.getPage;

@Repository
@RequiredArgsConstructor
public class RoomDao {

    private final MongoTemplate mongo;

    public Page<Room> findBy(RoomFilter filters, Pageable pageable) {
        Criteria criteria = new Criteria();

        ofNullable(filters.hotelName())
                .ifPresent(hotelName -> criteria.and("hotelName").is(hotelName));
        ofNullable(filters.number())
                .ifPresent(number -> criteria.and("number").is(number));
        ofNullable(filters.etaj())
                .ifPresent(etaj -> criteria.and("etaj").is(etaj));
        ofNullable(filters.tv())
                .ifPresent(tv -> criteria.and("facilities.tv").is(tv));
        ofNullable(filters.doubleBed())
                .ifPresent(doubleBed -> criteria.and("facilities.doubleBed").is(doubleBed));

        Query query = query(criteria).with(pageable);
        List<Room> content = mongo.find(query, Room.class);
        return getPage(content, pageable, () -> mongo.count(query(criteria), Room.class));
    }
}
