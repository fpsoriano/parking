package com.fabricio.parking.config;

import com.google.common.collect.ImmutableList;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
@Import(value = MongoAutoConfiguration.class)
public class MongoConfig {

  private static final Integer TIMEOUT = 120000;

  @Bean
  public MongoCustomConversions customConversions() {
    return new MongoCustomConversions(
        ImmutableList.of(
            DateToOffsetDateTimeConverter.INSTANCE, OffsetDateTimeToDateConverter.INSTANCE));
  }

  @ReadingConverter
  private static final class DateToOffsetDateTimeConverter
      implements Converter<Date, OffsetDateTime> {

    public static final DateToOffsetDateTimeConverter INSTANCE =
        new DateToOffsetDateTimeConverter();

    private DateToOffsetDateTimeConverter() {}

    @Override
    public OffsetDateTime convert(final Date source) {
      return source != null ? OffsetDateTime.ofInstant(source.toInstant(), ZoneOffset.UTC) : null;
    }
  }

  @WritingConverter
  private static final class OffsetDateTimeToDateConverter
      implements Converter<OffsetDateTime, Date> {

    public static final OffsetDateTimeToDateConverter INSTANCE =
        new OffsetDateTimeToDateConverter();

    private OffsetDateTimeToDateConverter() {}

    @Override
    public Date convert(final OffsetDateTime source) {
      if (source == null) {
        return null;
      }
      return Date.from(source.toInstant());
    }
  }

}