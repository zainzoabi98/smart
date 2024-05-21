package com.tsfn.loaders.feign;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsfn.loaders.bean.MarketingDataSummary;
import com.tsfn.loaders.service.MarketingDataService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/controller_loader")
@CrossOrigin("*")
public class SmartPostController {

	@Autowired
	private MarketingDataService marketingDataService;

	@GetMapping("/findMaxAndMinLikesWithPostsForUser/{accountId}")
	public Optional<MarketingDataSummary> findMaxAndMinLikesWithPostsForUser(@PathVariable Long accountId) {

		return marketingDataService.findMaxAndMinLikesWithPostsForUser(accountId);
	}
}
