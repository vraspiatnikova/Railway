package com.tsystems.jschool.railway.webServices;

import com.tsystems.jschool.railway.dto.BoardByStationDto;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.interfaces.BoardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestBoardController {

    private static final Logger LOGGER = Logger.getLogger(RestStationController.class);
    private final BoardService boardService;

    @Autowired
    public RestBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(value = "/getStationBoard/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public List<BoardByStationDto> getAll(@PathVariable String stationName) {
        List<BoardByStationDto> boardByStationDtos = null;
        try {
            boardByStationDtos = boardService.getAllBoardByStationDto(stationName);
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
        }
        return boardByStationDtos;
    }
}
