package com.tsfn.loaders.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tsfn.loaders.bean.MarketingData;
import com.tsfn.loaders.bean.MarketingDataSummary;

@Repository
public interface MarketingDataRepository extends JpaRepository<MarketingData, Long> {

	@Query(value = "SELECT SUM(" + "CASE WHEN :columnName = 'impressions' THEN CAST(m.impressions AS DOUBLE) "
			+ "     WHEN :columnName = 'views' THEN CAST(m.views AS DOUBLE) "
			+ "     WHEN :columnName = 'clicks' THEN CAST(m.clicks AS DOUBLE) "
			+ "     WHEN :columnName = 'ctr' THEN m.ctr "
			+ "     WHEN :columnName = 'likes' THEN CAST(m.likes AS DOUBLE) "
			+ "     WHEN :columnName = 'comments' THEN CAST(m.comments AS DOUBLE) "
			+ "     WHEN :columnName = 'shares' THEN CAST(m.shares AS DOUBLE) "
			+ "     WHEN :columnName = 'engagementRate' THEN m.engagement_rate " + "END) " + "FROM Marketing m "
			+ "WHERE m.account_id = :accountId " + "AND m.timestamp <= :startTime AND m.timestamp >= :endTime "
			+ "GROUP BY m.account_id " + "HAVING SUM("
			+ "CASE WHEN :columnName = 'impressions' THEN CAST(m.impressions AS DOUBLE) "
			+ "     WHEN :columnName = 'views' THEN CAST(m.views AS DOUBLE) "
			+ "     WHEN :columnName = 'clicks' THEN CAST(m.clicks AS DOUBLE) "
			+ "     WHEN :columnName = 'ctr' THEN m.ctr "
			+ "     WHEN :columnName = 'likes' THEN CAST(m.likes AS DOUBLE) "
			+ "     WHEN :columnName = 'comments' THEN CAST(m.comments AS DOUBLE) "
			+ "     WHEN :columnName = 'shares' THEN CAST(m.shares AS DOUBLE) "
			+ "     WHEN :columnName = 'engagementRate' THEN m.engagement_rate " + "END)", nativeQuery = true)
	Double hasEnoughClicksByAccountIdAndTimeRange(@Param("accountId") long accountId,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("columnName") String columnName);

	int countByFileUploaded(String fileName);

	@Query("SELECT new com.tsfn.loaders.bean.MarketingDataSummary(" + "m.accountId, MAX(m.likes), MIN(m.likes), "
			+ "(SELECT md.description FROM MarketingData md WHERE md.likes = MAX(m.likes) AND md.accountId = :accountId AND md.id = (SELECT MIN(md2.id) FROM MarketingData md2 WHERE md2.likes = MAX(m.likes) AND md2.accountId = :accountId)), "
			+ "(SELECT md.description FROM MarketingData md WHERE md.likes = MIN(m.likes) AND md.accountId = :accountId AND md.id = (SELECT MIN(md2.id) FROM MarketingData md2 WHERE md2.likes = MIN(m.likes) AND md2.accountId = :accountId)) "
			+ ") " + "FROM MarketingData m WHERE m.accountId = :accountId GROUP BY m.accountId")
	Optional<MarketingDataSummary> findMaxAndMinLikesWithPostsForUser(Long accountId);
}
