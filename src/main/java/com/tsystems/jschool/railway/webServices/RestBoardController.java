package com.tsystems.jschool.railway.webServices;

import com.tsystems.jschool.railway.dto.BoardByStationDto;
import com.tsystems.jschool.railway.services.interfaces.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestBoardController {

    private final BoardService boardService;

    @Autowired
    public RestBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(value = "/getStationBoard", method = RequestMethod.GET)
    public List<BoardByStationDto> getAll() throws Exception {
        return boardService.getAllBoardByStationDto("Sochi");
    }
}
