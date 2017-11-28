package com.tsystems.jschool.railway.webservices;

import com.tsystems.jschool.railway.dto.BoardByStationDto;
import com.tsystems.jschool.railway.services.interfaces.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
public class RestBoardController {

    private final BoardService boardService;

    @Autowired
    public RestBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(value = "/getStationBoard/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public List<BoardByStationDto> getAll(@PathVariable String stationName) {
        return boardService.getFirstTenBoardByStationDtos(stationName);
    }
}
