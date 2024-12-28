package icu.Liki4.signin.service;

import cn.hutool.core.io.file.FileReader;
import org.springframework.stereotype.Service;

@Service
public class FlagTestService {
    public String catFlag() {
        FileReader fileReader = new FileReader("/flag");
        return fileReader.readString();
    }
}
