package dataBase.repository.test;

import dataBase.entity.test.WebReportFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface WebReportFolderRepository extends JpaRepository<WebReportFolder, Long> {

    List<WebReportFolder> findAllByLevelGreaterThanAndIdNotInOrderByLevelDesc(Long level, Collection<Long> parentId);

    List<WebReportFolder> findAllByParentIdEqualsOrderByVisibleOrder(Long parentId);

    List<WebReportFolder> findAllByParentIdEqualsOrderByName(Long parentId);

    List<WebReportFolder> findAllByParentIdIsNull();
}
