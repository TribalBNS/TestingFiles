package guru.qa.tests.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuJson {
    public Menu menu;


    public static class Menu {
        public String id;
        public String value;
        public List list;
    }

    public static class List {
        public Items items;
    }

    //    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Items {
        @JsonProperty("new_doc")
        public NewDoc newDoc;
        @JsonProperty("open_doc")
        public OpenDoc openDoc;
        @JsonProperty("save_doc")
        public SaveDoc saveDoc;
        @JsonProperty("save_as_doc")
        public SaveAsDoc saveAsDoc;
        @JsonProperty("print_option")
        public PrintOption printOption;
    }

    public static class NewDoc {
        public String value;
        public String onclick;
    }

    public static class OpenDoc {
        public String value;
        public String onclick;
    }

    public static class SaveDoc {
        public String value;
        public String onclick;
    }


    private static class SaveAsDoc {
        public String value;
        public String onclick;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class PrintOption {
        public String value;
    }
}
