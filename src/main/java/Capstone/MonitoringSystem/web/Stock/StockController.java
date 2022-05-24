package Capstone.MonitoringSystem.web.Stock;

import Capstone.MonitoringSystem.domain.Stock.Stock;
import Capstone.MonitoringSystem.domain.Search;
import Capstone.MonitoringSystem.domain.Stock.StockUpdateForm;
import Capstone.MonitoringSystem.domain.Stock.stockservice.StockService;
import Capstone.MonitoringSystem.domain.Storage.storagerepository.StorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final StockService stockService;
    private final StorageRepository sr;

    @PostMapping("/stocks/new")
    public String stockInput(@Validated @ModelAttribute("form") StockInputForm form,
                             BindingResult bindingResult) {

        if (idDuplicatedCheck(form.getId())) {
            bindingResult.rejectValue("id", "DuplicatedId");
            return "inputListUpload";
        }

        if (bindingResult.hasErrors()) {
            return "inputListUpload";
        }

        stockService.saveStock(form.getId(), form.getName(), form.getDryingPlace(), form.getQuantity(),
                form.getPrice(), form.getStockedDate(), form.getYield(), form.getStorageId());

        return "redirect:/stocks";
    }

    @GetMapping("/stocks")
    public String stockList(@ModelAttribute("search") Search search, Model model) {
        List<Stock> stocks = stockService.findStocksBySearch(search);
        model.addAttribute("stocks", stocks);
        return "listPage";
    }

    @PostMapping("/stocks/update")
    public String stockUpdate(@Validated @ModelAttribute("stock") StockUpdateForm form,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "stockModifyPage";
        }

        stockService.updateStock(form);

        return "redirect:/stocks";
    }

    private boolean idDuplicatedCheck(Long id) {
        List<Stock> all = stockService.findAll();

        for (Stock stock : all) {
            if (stock.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}