package com.webbanhangnongsan.vn.webbanhangnongsan.service.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportAdminService {
    @Autowired
    OrderDetailRepository repo;
    private final static int pageSize = 1;
    public int getPageSize() {return pageSize;}
    // paginating product sold
    public List<Object[]> paginatedRepo(String search, int currentPage) {
        int offSet = (currentPage - 1) * pageSize;
        List<Object[]> searchRepoList = repo.repo()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            return repo.repo()
                    .stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
        else {
            return searchRepoList.stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
    }
    // total page product sold
    public int totalRepoPage(String search) {
        List<Object[]> searchRepoList = repo.repo()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            long repoQuantity = repo.repo().size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
        else {
            int repoQuantity = searchRepoList.size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
    }
    // paginating category sold
    public List<Object[]> repoWhereCategory(String search, int currentPage) {
        int offSet = (currentPage - 1) * pageSize;
        List<Object[]> searchRepoList = repo.repoWhereCategory()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            return repo.repoWhereCategory()
                    .stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
        else {
            return searchRepoList.stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
    }
    // total page category sold
    public int totalRepoWhereCategory(String search) {
        List<Object[]> searchRepoList = repo.repoWhereCategory()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            long repoQuantity = repo.repoWhereCategory().size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
        else {
            int repoQuantity = searchRepoList.size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
    }
    // paginating year sold
    public List<Object[]> repoWhereYear(String search, int currentPage) {
        int offSet = (currentPage - 1) * pageSize;
        List<Object[]> searchRepoList = repo.repoWhereYear()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            return repo.repoWhereYear()
                    .stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
        else {
            return searchRepoList.stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
    }
    // total page year sold
    public int totalRepoWhereYear(String search) {
        List<Object[]> searchRepoList = repo.repoWhereYear()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            long repoQuantity = repo.repoWhereYear().size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
        else {
            int repoQuantity = searchRepoList.size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
    }
    // paginating quarter sold
    public List<Object[]> repoWhereQUARTER(String search, int currentPage) {
        int offSet = (currentPage - 1) * pageSize;
        List<Object[]> searchRepoList = repo.repoWhereQUARTER()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            return repo.repoWhereQUARTER()
                    .stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
        else {
            return searchRepoList.stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
    }
    // total page quarter sold
    public int totalRepoWhereQUARTER(String search) {
        List<Object[]> searchRepoList = repo.repoWhereQUARTER()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            long repoQuantity = repo.repoWhereQUARTER().size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
        else {
            int repoQuantity = searchRepoList.size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
    }
    // paginating month sold
    public List<Object[]> repoWhereMonth(String search, int currentPage) {
        int offSet = (currentPage - 1) * pageSize;
        List<Object[]> searchRepoList = repo.repoWhereMonth()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            return repo.repoWhereMonth()
                    .stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
        else {
            return searchRepoList.stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
    }
    // total page month sold
    public int totalRepoWhereMonth(String search) {
        List<Object[]> searchRepoList = repo.repoWhereMonth()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            long repoQuantity = repo.repoWhereMonth().size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
        else {
            int repoQuantity = searchRepoList.size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
    }
    // pagination user sold
    public List<Object[]> reportCustomer(String search, int currentPage) {
        int offSet = (currentPage - 1) * pageSize;
        List<Object[]> searchRepoList = repo.reportCustomer()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            return repo.reportCustomer()
                    .stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
        else {
            return searchRepoList.stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
    }
    // total page user sold
    public int totalReportCustomer(String search) {
        List<Object[]> searchRepoList = repo.reportCustomer()
                .stream()
                .filter(object -> object[0].toString().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (searchRepoList.isEmpty()) {
            long repoQuantity = repo.reportCustomer().size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
        else {
            int repoQuantity = searchRepoList.size();
            return (int) Math.ceil((double) repoQuantity / pageSize);
        }
    }
}
