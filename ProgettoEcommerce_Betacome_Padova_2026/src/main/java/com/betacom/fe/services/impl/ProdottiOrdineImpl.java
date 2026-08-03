package com.betacom.fe.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.ProdottiOrdineReq;
import com.betacom.fe.dto.output.ProdottiOrdineDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.ProdottiOrdineMapper;
import com.betacom.fe.models.DivisioneProdotto;
import com.betacom.fe.models.Indirizzi;
import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.Prodotti;
import com.betacom.fe.models.ProdottiCarrello;
import com.betacom.fe.models.ProdottiOrdine;
import com.betacom.fe.repositories.IDivisioneProdottoRepository;
import com.betacom.fe.repositories.IIndirizziRepository;
import com.betacom.fe.repositories.IOrdineRepository;
import com.betacom.fe.repositories.IProdottiCarrelloRepository;
import com.betacom.fe.repositories.IProdottiOrdineRepository;
import com.betacom.fe.repositories.IProdottiRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.INotificaServices;
import com.betacom.fe.services.interfaces.IProdottiOrdineServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdottiOrdineImpl implements IProdottiOrdineServices {

	private final IProdottiRepository proR;
	private final IProdottiCarrelloRepository procarR;
	private final IProdottiOrdineRepository prordR;
	private final IOrdineRepository ordR;
	private final IIndirizziRepository indR;
	private final IMessaggioServices msgS;
	private final IDivisioneProdottoRepository divR;
	private final INotificaServices notificaS;

	@Transactional
	@Override
	public void create(ProdottiOrdineReq req) throws Exception {

		Ordini ordine = ordR.findById(req.getOrdineId())
				.orElseThrow(() -> new AcademyException(msgS.get("ordine.non.esiste")));

		Prodotti prodotto = proR.findById(req.getProdottoId())
				.orElseThrow(() -> new AcademyException(msgS.get("prod.non.esiste")));

		Indirizzi indirizzo = indR.findById(req.getIndirizzoSpedizioneId())
				.orElseThrow(() -> new AcademyException(msgS.get("indirizzo.non.esiste")));

		ProdottiCarrello prodottiCar = procarR.findById(req.getProdottiCarrelloId())
				.orElseThrow(() -> new AcademyException(msgS.get("prodcar.non.esiste")));

		DivisioneProdotto divisione = divR.findById(req.getDivisioneOrdineId())
				.orElseThrow(() -> new AcademyException(msgS.get("divisione.non.esiste")));

		Integer quantita = prodottiCar.getQuantita();

		if (quantita == null || quantita <= 0) {

			throw new AcademyException(msgS.get("quantita.non.valida"));
		}

		if (divisione.getQuantitaDisponibile() < quantita) {

			throw new AcademyException(msgS.get("quantita.non.disponibile"));
		}

		if (!divisione.getProdotto().getIdProdotto().equals(prodotto.getIdProdotto())) {

			throw new AcademyException(msgS.get("divisione.prodotto.non.valida"));
		}

		if (!prodottiCar.getDivisione().getIdDivisione().equals(divisione.getIdDivisione())) {

			throw new AcademyException(msgS.get("divisione.prodotto.non.valida"));
		}

		ProdottiOrdine prodottoOrdine = new ProdottiOrdine();

		prodottoOrdine.setOrdine(ordine);
		prodottoOrdine.setProdotto(prodotto);
		prodottoOrdine.setIndirizzoSpedizione(indirizzo);
		prodottoOrdine.setDivisioneOrdine(divisione);

		prodottoOrdine.setQuantita(quantita);
		prodottoOrdine.setPrezzo(prodotto.getPrezzo());

		divisione.setQuantitaDisponibile(divisione.getQuantitaDisponibile() - quantita);

		prordR.save(prodottoOrdine);

		divR.save(divisione);

		if (divisione.getStockAlert() != null && divisione.getQuantitaDisponibile() <= divisione.getStockAlert()) {

			notificaS.creaStockAlert(divisione);
		}

	}

	@Override
	public ProdottiOrdineDTO getById(Integer idItem) throws Exception {

		ProdottiOrdine prodottoOrdine = prordR.findById(idItem)
				.orElseThrow(() -> new AcademyException(msgS.get("ordine.non.esiste")));

		return ProdottiOrdineMapper.toDTO(prodottoOrdine);
	}

	@Override
	public List<ProdottiOrdineDTO> getAll() throws Exception {

		return prordR.findAll().stream().map(ProdottiOrdineMapper::toDTO).toList();

	}

	// ACQUISTI CLIENTE
	@Override
	public List<ProdottiOrdineDTO> getByCliente(Integer userId) throws Exception {

		return prordR.findByOrdine_UserId_UserId(userId).stream().map(ProdottiOrdineMapper::toDTO).toList();

	}

	// ACQUISTI CLIENTE CON DATE
	@Override
	public List<ProdottiOrdineDTO> getByCliente(Integer userId, LocalDate dataInizio, LocalDate dataFine)
			throws Exception {

		if (dataInizio == null || dataFine == null) {

			return getByCliente(userId);

		}

		return prordR.findByOrdine_UserId_UserIdAndOrdine_DataBetween(userId, dataInizio, dataFine).stream()
				.map(ProdottiOrdineMapper::toDTO).toList();

	}

	// VENDITE DEL VENDITORE
	@Override
	public List<ProdottiOrdineDTO> getByVenditore(Integer userId) throws Exception {

		return prordR.findByProdotto_Venditore_UserId(userId).stream().map(ProdottiOrdineMapper::toDTO).toList();

	}

	// VENDITE DEL VENDITORE CON DATE
	@Override
	public List<ProdottiOrdineDTO> getByVenditore(Integer userId, LocalDate dataInizio, LocalDate dataFine)
			throws Exception {

		if (dataInizio == null || dataFine == null) {

			return getByVenditore(userId);

		}

		return prordR.findByProdotto_Venditore_UserIdAndOrdine_DataBetween(userId, dataInizio, dataFine).stream()
				.map(ProdottiOrdineMapper::toDTO).toList();

	}

}