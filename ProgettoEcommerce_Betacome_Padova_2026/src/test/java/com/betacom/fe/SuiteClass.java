package com.betacom.fe;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.betacom.fe.carrello.CarrelloTest;
import com.betacom.fe.tipo.CategoriaTest;
import com.betacom.fe.tipo.DivisioneProdottoTest;
import com.betacom.fe.tipo.OrdineTest;
import com.betacom.fe.tipo.ProdottoTest;
import com.betacom.fe.tipo.RuoliTest;
import com.betacom.fe.tipo.SottoCategoriaTest;
import com.betacom.fe.tipo.StatoOrdineTest;
import com.betacom.fe.tipo.StatoPagamentoTest;
import com.betacom.fe.user.DeleteUserTest;
import com.betacom.fe.user.IndirizzoTest;
import com.betacom.fe.user.UserTest;


@Suite
@SelectClasses({
	CategoriaTest.class,
	SottoCategoriaTest.class,
	RuoliTest.class,
	StatoPagamentoTest.class,
	StatoOrdineTest.class,
	UserTest.class,
	IndirizzoTest.class,
	OrdineTest.class,
	ProdottoTest.class,
	//DivisioneProdottoTest.class,
	DeleteUserTest.class
})
public class SuiteClass {
}
