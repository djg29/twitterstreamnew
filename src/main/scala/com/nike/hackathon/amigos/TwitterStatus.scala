package com.nike.hackathon.amigos



import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class TwitterStatus(id:Option[String] = None, text:Option[String] = None, source:Option[String] = None,
                         location: Option[String] = None, place:Option[String] = None, user:Option[String] = None, retweetCount: Option[Int] = None)

object TwitterStatus extends DefaultJsonProtocol  {
  implicit val format: RootJsonFormat[TwitterStatus] = jsonFormat7(TwitterStatus.apply)
}


/**
  Date getCreatedAt();

    long getId();

    String getText();

    int getDisplayTextRangeStart();

    int getDisplayTextRangeEnd();

    String getSource();

    boolean isTruncated();

    long getInReplyToStatusId();

    long getInReplyToUserId();

    String getInReplyToScreenName();

    GeoLocation getGeoLocation();


    Place getPlace();

    boolean isFavorited();

    boolean isRetweeted();

    int getFavoriteCount();

    User getUser();

    boolean isRetweet();

    Status getRetweetedStatus();

    long[] getContributors();

    int getRetweetCount();

    boolean isRetweetedByMe();

    long getCurrentUserRetweetId();

    boolean isPossiblySensitive();

    String getLang();

    Scopes getScopes();

    String[] getWithheldInCountries();

    long getQuotedStatusId();

    Status getQuotedStatus(); **/
