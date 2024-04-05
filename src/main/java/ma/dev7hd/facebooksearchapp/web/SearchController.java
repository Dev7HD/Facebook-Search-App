package ma.dev7hd.facebooksearchapp.web;

import lombok.AllArgsConstructor;
import ma.dev7hd.facebooksearchapp.entities.FBUser;
import ma.dev7hd.facebooksearchapp.repository.IUserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@AllArgsConstructor
public class SearchController {
    private IUserRepository userRepository;
    private static final String regex = "^[a-z]+(?: [a-z]+)?$";
    private static final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/error")
    public String home2(){
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/searchbyid")
    public String searchById(@RequestParam(defaultValue = "0") Long id, Model model) {
        if(id >= 26702) {
            FBUser user = userRepository.findById(id).orElse(null);
            if (user != null) {
                model.addAttribute("user", user);
                return "index";
            } else {
                return "redirect:/?userNotFound";
            }
        } else return "redirect:/?Oops";
    }

    @GetMapping("/searchbyphone")
    public String searchByPhone(@RequestParam(defaultValue = "0") Long phone, Model model) {
        if(phone != 0) {
            FBUser user = userRepository.findByPhone(phone);
            if(user != null) {
                model.addAttribute("user", user);
                return "index";
            } else {
                return "redirect:/?userNotFound";
            }
        } else return "redirect:/?Oops";
    }

    @GetMapping("/searchbyemail")
    public String searchByEmail(@RequestParam String email, Model model) {
        if(!email.isEmpty()) {
            FBUser user = userRepository.findByEmail(email);
            if(user != null) {
                model.addAttribute("user", user);
                return "index";
            } else {
                return "redirect:/?userNotFound";
            }
        } else return "redirect:/?Oops";
    }

    @GetMapping("/searchbylastname")
    public String searchByLastName(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "100") int size,
            @RequestParam(name = "lastname") String lastName
    ) {

        Matcher matcher = pattern.matcher(lastName);
        boolean matches = matcher.matches();
        if(!lastName.isEmpty() && matches) {
            Slice<FBUser> users = userRepository.findAllByLastNameStartingWith(lastName, PageRequest.of(page, size));
            if(users.stream().findAny().isPresent()) {
                int totalResults = userRepository.countByLastNameStartingWith(lastName);
                int pages = pageCount(totalResults,size);
                model.addAttribute("users", users.getContent());
                model.addAttribute("currentPage", page);
                model.addAttribute("lastname", lastName);
                model.addAttribute("size", size);
                model.addAttribute("pages", pages);
                model.addAttribute("totalResults", totalResults);
                return "table-search-results";
            } else {
                return "redirect:/?userNotFound";
            }
        } else return "redirect:/?Oops";
    }

    @GetMapping("/searchby_lastname")
    public String toSearchByLastName(
            Model model,
            @RequestParam int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(name = "lastname") String lastName,
            @RequestParam int pages,
            @RequestParam(name = "t") int totalResults
    ) {
        Matcher matcher = pattern.matcher(lastName);
        boolean matches = matcher.matches();
        if(!lastName.isEmpty() && matches) {
            Slice<FBUser> users = userRepository.findAllByLastNameStartingWith(lastName, PageRequest.of(page, size));
            model.addAttribute("users", users.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("lastname", lastName);
            model.addAttribute("size", size);
            model.addAttribute("pages", pages);
            model.addAttribute("totalResults", totalResults);
            return "table-search-results";
        } else return "redirect:/?Oops";
    }

    @GetMapping("/searchbyfullname")
    public String searchByFullName(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "100") int size,
            @RequestParam(name = "firstname") String firstName,
            @RequestParam(name = "lastname") String lastName
    ) {
        Matcher matcherFirstName = pattern.matcher(firstName);
        Matcher matcherLastName = pattern.matcher(lastName);
        boolean matchesFirstName = matcherFirstName.matches();
        boolean matchesLastName = matcherLastName.matches();
        if(!firstName.isEmpty() && !lastName.isEmpty() && matchesFirstName && matchesLastName) {
            Slice<FBUser> users = userRepository.findAllByFirstNameStartingWithAndLastNameStartingWith(firstName,lastName, PageRequest.of(page, size));
            if(users.stream().findAny().isPresent()) {
                int totalResults = userRepository.countByFirstNameStartingWithAndLastNameStartingWith(firstName,lastName);
                int pages = pageCount(totalResults, size);
                System.out.println(pages);
                model.addAttribute("users", users.getContent());
                model.addAttribute("currentPage", page);
                model.addAttribute("firstname", firstName);
                model.addAttribute("lastname", lastName);
                model.addAttribute("size", size);
                model.addAttribute("pages", pages);
                model.addAttribute("totalResults", totalResults);
                return "table-search-results-byfullname";
            } else {
                return "redirect:/?userNotFound";
            }
        } else return "redirect:/?Oops";
    }

    @GetMapping("/searchby_fullname")
    public String toSearchByFullName(
            Model model,
            @RequestParam int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(name = "firstname") String firstName,
            @RequestParam(name = "lastname") String lastName,
            @RequestParam int pages,
            @RequestParam(name = "t") int totalResults
    ) {
        Matcher matcherFirstName = pattern.matcher(firstName);
        Matcher matcherLastName = pattern.matcher(lastName);
        boolean matchesFirstName = matcherFirstName.matches();
        boolean matchesLastName = matcherLastName.matches();
        if(!firstName.isEmpty() && !lastName.isEmpty() && matchesFirstName && matchesLastName) {
            Slice<FBUser> users = userRepository.findAllByFirstNameStartingWithAndLastNameStartingWith(firstName,lastName, PageRequest.of(page, size));
            model.addAttribute("users", users.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("firstname", firstName);
            model.addAttribute("lastname", lastName);
            model.addAttribute("size", size);
            model.addAttribute("pages", pages);
            model.addAttribute("totalResults", totalResults);
            return "table-search-results-byfullname";
        } else return "redirect:/?Oops";
    }

    public int pageCount(int totalResults, int size) {
        int pages = totalResults / size; // Calculate the number of complete pages
        if (totalResults % size != 0) {
            pages++;
        }
        return pages;
    }
}
