package com.nike.hackathon.amigos

import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener}

class Listener extends StatusListener {

  override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {}

  override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = System.out.println(userId)

  override def onStatus(status: Status): Unit = {
    System.out.println(status.getText())
  }

  override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = {}

  override def onStallWarning(warning: StallWarning): Unit = {}

  override def onException(ex: Exception): Unit = {}

}
