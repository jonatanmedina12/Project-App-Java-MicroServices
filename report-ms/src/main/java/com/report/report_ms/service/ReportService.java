package com.report.report_ms.service;

import com.netflix.discovery.EurekaClient;
import com.report.report_ms.helpers.ReportHelper;
import com.report.report_ms.interfaces.ReportInterface;
import com.report.report_ms.models.Company;
import com.report.report_ms.models.WebSite;
import com.report.report_ms.repositories.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ReportService implements ReportInterface {
    @Autowired
    private CompaniesRepository companiesRepository;
    @Autowired
    private ReportHelper reportHelper;



    @Override
    public String makeReport(String name) {

      return   reportHelper.readTemplate(this.companiesRepository.getByName(name).orElseThrow());



    }

    @Override
    public String saveReport(String nameReport) {
        var format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var placeHolders = this.reportHelper.getPlaceholdersFromTemplate(nameReport);

        var webSites = Stream.of(placeHolders.get(3))
                .map(website -> {
                    WebSite site = new WebSite();
                    site.setName(website);
                    return site;
                })
                .toList();

        Company company = new Company();
        company.setName(placeHolders.get(0));
        company.setLogo("Logo");
        company.setFounder(placeHolders.get(2));
        company.setFoundationDate(LocalDate.parse(String.format(placeHolders.get(1))));
        company.setWebSites(webSites);

        this.companiesRepository.postByName(company);

        return "saved";
    }

    @Override
    public void deleteReport(String nameReport) {

        this.companiesRepository.deleteByName(nameReport);

    }
}
