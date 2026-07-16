package com.betacom.fe.utils;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import com.betacom.fe.models.Sconto;
import com.betacom.fe.repositories.IScontoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask 
{
	private final IScontoRepository repS;

    @Scheduled(cron = "0 59 23 * * *")
    @Transactional
    public void eliminaScontiScaduti() 
    {
        log.info("Esecuzione task pianificata: eliminazione sconti scaduti");
        
        LocalDate oggi = LocalDate.now();
        List<Sconto> scaduti = repS.findByDataFineBefore(oggi);
        
        if (!scaduti.isEmpty()) 
        {
            repS.deleteAll(scaduti);
            log.info("Eliminati {} sconti scaduti.", scaduti.size());
        } 
        else 
        {
            log.info("Nessuno sconto scaduto trovato per oggi.");
        }
    }
}
