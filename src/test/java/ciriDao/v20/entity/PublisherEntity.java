package ciriDao.v20.entity;

import es.cesguiro.common.annotations.Id;
import es.cesguiro.common.annotations.TableName;
import es.cesguiro.dao.v20.entity.CiriEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("publishers")
public class PublisherEntity extends CiriEntity {

    @Id
    private Integer id;
    private String name;

    public PublisherEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
