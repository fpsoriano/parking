package com.fabricio.parking.helper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetDateTimeHelper {

  private OffsetDateTimeHelper() {}

  /**
   * Converts the parameter to an instance of OffsetDateTime using the UTC as ZoneId.
   *
   * @param timestamp to be converted to OffsetDateTime. If the timestamp is null, the method will
   *     retrieve the current timestamp to convert.
   * @return an instance of OffsetDateTime
   */
  public static OffsetDateTime fromTimestamp(final Long timestamp) {

    final ZoneId utcZone = ZoneOffset.UTC;
    if (timestamp == null) {
      return OffsetDateTime.now(utcZone);
    }

    final Instant instant = Instant.ofEpochMilli(timestamp);
    return OffsetDateTime.ofInstant(instant, utcZone);
  }
}
