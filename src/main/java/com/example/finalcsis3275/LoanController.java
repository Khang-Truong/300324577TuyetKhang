package com.example.finalcsis3275;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    static int num=0;

    @GetMapping("/index")
    public String showIndexPage(Model model) {
        List<loantable> listRecords;
        listRecords = loanRepository.findAll();
        model.addAttribute("listRecords", listRecords);
        return "displayAllRecords";
    }

    @GetMapping("/")
    public String showIndexPage2(Model model) {
        List<loantable> listRecords;
        listRecords = loanRepository.findAll();
        model.addAttribute("listRecords", listRecords);
        return "displayAllRecords";
    }

    @GetMapping("/delete")
    public String deleteRecord(String clientno, ModelMap mm, HttpSession httpSession) {
        loanRepository.deleteById(clientno);
        return "redirect:index";
    }

    @GetMapping("/addPage")
    public String showAddPage(Model model) {
        num=1;
        //create empty  context
        model.addAttribute("record", new loantable());

        //for select/option dropdown list
        List<String> savingsTypeList = Arrays.asList("Business", "Personal");
        model.addAttribute("savingsTypeList", savingsTypeList);
        return "addRecord";
    }

    @GetMapping("/editRecords")
    public String showEditPage(Model model, String clientno, HttpSession session) {
        num = 2;
//        session.setAttribute("info", 0);

        //for select/option dropdown list
        List<String> savingsTypeList = Arrays.asList("Business", "Personal");
        model.addAttribute("savingsTypeList", savingsTypeList);

        loantable loanT = loanRepository.findById(clientno).orElse(null);
        if (loanT == null) throw new RuntimeException("Student does not exist");
        model.addAttribute("record", loanT);
        return "editRecord";
    }

    @PostMapping(path = "/save")
    public String save(Model model, loantable loanT, BindingResult
            bindingResult, ModelMap mm, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "addRecord";
        } else {
            //if add -> check exist -> add
            if(num!=2){
                Optional<loantable> checkLoan=loanRepository.findById(loanT.getClientno());
                if(checkLoan.isPresent()){
                    mm.put("e",1);
                }else{
                    loanRepository.save(loanT);
                }
            }else { //if edit -> just edit
                loanRepository.save(loanT);
            }

//            if (num == 2) {
//                mm.put("e", 2);
//                mm.put("a", 0);
//            } else {
//                mm.put("a", 1);
//                mm.put("e", 0);
//            }
            return "redirect:index";
        }
    }

    @GetMapping("/amortizationPage")
    public String showAmortizationPage(Model model, String clientno, HttpSession session) {
//        num = 2;
//        session.setAttribute("info", 0);

//        //for select/option dropdown list
//        List<String> savingsTypeList = Arrays.asList("Business", "Personal");
//        model.addAttribute("savingsTypeList", savingsTypeList);



        loantable loanT = loanRepository.findById(clientno).orElse(null);
        if (loanT == null) throw new RuntimeException("Student does not exist");

        int years=loanT.getYears();
        double loanAmount=loanT.getLoananmount();
        String loanType=loanT.getLoantype();
        int interestRate=0;
        if(loanType=="Personal") {
            interestRate=6;
        }else{
            interestRate=9;
        }
        double monthlyPayment=(loanAmount*interestRate/100)/(1-Math.pow(1+interestRate,-years));
        model.addAttribute("record", loanT);
        model.addAttribute("monthlyPayment",monthlyPayment);
        return "amortizationTable";
    }
}
