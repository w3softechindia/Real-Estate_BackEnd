package com.realestate.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedbackRequest {
	private String email;
    private String feedbackLink;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedbackLink() {
        return feedbackLink;
    }

    public void setFeedbackLink(String feedbackLink) {
        this.feedbackLink = feedbackLink;
    }
}
