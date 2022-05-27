package dataBase.entity.test;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Immutable
@Table(name = "impl_test")
public class ImplTest {
    @Id
    private Long id;

    @Column(name = "tags")
    private String tags;

    @Column(name = "relative_path")
    private String relativePath;
}
