package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Log;
import de.dhbw.erpbackend.repository.LogRepository;
import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.PageRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Path("/logs")
@Produces(MediaType.APPLICATION_JSON)
public class LogResource {

    private static final int DEFAULT_PAGE_SIZE = 25;
    private static final int MAX_PAGE_SIZE = 200;

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.GERMANY)
                    .withZone(ZoneId.systemDefault());

    @Inject
    private LogRepository logRepository;

    @GET
    public LogPage list(@QueryParam("draw") @DefaultValue("0") int draw,
                        @QueryParam("start") @DefaultValue("0") int start,
                        @QueryParam("length") @DefaultValue("25") int length,
                        @QueryParam("search[value]") @DefaultValue("") String search) {

        if (length <= 0 || length > MAX_PAGE_SIZE) {
            length = DEFAULT_PAGE_SIZE;
        }
        if (start < 0) {
            start = 0;
        }
        long pageNumber = (start / length) + 1;
        PageRequest pageRequest = PageRequest.ofPage(pageNumber, length, false);

        long recordsTotal = logRepository.countAll();
        List<Log> rows;
        long recordsFiltered;

        if (search == null || search.isBlank()) {
            rows = logRepository.findAll(pageRequest, Order.by(Sort.desc("created"))).content();
            recordsFiltered = recordsTotal;
        } else {
            String term = search.trim();
            rows = logRepository.search(term, pageRequest).content();
            recordsFiltered = logRepository.countSearch(term);
        }

        List<LogRow> data = rows.stream()
                .map(l -> new LogRow(
                        l.getCreated() == null ? "" : FMT.format(l.getCreated()),
                        l.getUser() == null ? "" : l.getUser().getUsername(),
                        l.getType() == null ? "" : l.getType().name(),
                        l.getDescription()))
                .toList();

        return new LogPage(draw, recordsTotal, recordsFiltered, data);
    }

    @Getter
    @AllArgsConstructor
    public static class LogPage {
        private int draw;
        private long recordsTotal;
        private long recordsFiltered;
        private List<LogRow> data;
    }

    @Getter
    @AllArgsConstructor
    public static class LogRow {
        private String created;
        private String user;
        private String type;
        private String description;
    }
}
