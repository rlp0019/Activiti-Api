package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lector.FabricaConexion;
import lector.FabricaConexionGitHub;
import lector.FachadaConexion;

public class PrincipalTest {
	private FabricaConexion fabricaLector;

	@Before
	public void setUp() {
		fabricaLector = FabricaConexionGitHub.getInstance();
	}

	@After
	public void tearDown() {
		fabricaLector = null;
	}

	@Test
	public void testConexion() {
		String usuario;
		String password;
		usuario = "pruebarlp";
		password = "12qe34wr";

		try {
			FachadaConexion lector = fabricaLector.crearFachadaConexion(usuario, password);

			String[] repositorios = lector.getNombresRepositorio("pruebarlp");

			assertEquals(repositorios.length, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IOException.class)
	public void testRepositorioVacio() throws IOException {
		String usuario;
		String password;
		usuario = "pruebarlp";
		password = "12qe34wr";

		FachadaConexion lector = fabricaLector.crearFachadaConexion(usuario, password);

		lector.getNombresRepositorio("pruebarlp");

		lector.obtenerMetricas("pruebarlp", "ProyectoVacio");
	}

	@Test
	public void testRepositorioGrande() {
		String usuario;
		String password;
		usuario = "pruebarlp";
		password = "12qe34wr";
		FachadaConexion lector = null;

		try {
			lector = fabricaLector.crearFachadaConexion(usuario, password);

			lector.getNombresRepositorio("railsgirls");

			lector.obtenerMetricas("railsgirls", "railsgirls.github.io");
			Object[] resultadoMetricas = lector.getResultados();
			String cadena = "Metricas:" + "\n  NumeroIssues: 400" + "\n  ContadorTareas: 0,38"
					+ "\n  NumeroIssuesCerradas: 377" + "\n  PorcentajeIssuesCerradas: 94,00"
					+ "\n  MediaDiasCierre: 31,23" + "\n  NumeroCambiosSinMensaje: 0" + "\n  MediaDiasCambio: 2,36"
					+ "\n  DiasPrimerUltimoCommit: 2511,56" + "\n  UltimaModificacion: Tue Mar 05 06:55:00 CET 2019"
					+ "\n  CommitPorMes: " + "\n\tEnero: 60" + "\n\tFebrero: 74" + "\n\tMarzo: 112" + "\n\tAbril: 182"
					+ "\n\tMayo: 105" + "\n\tJunio: 65" + "\n\tJulio: 82" + "\n\tAgosto: 106" + "\n\tSeptiembre: 79"
					+ "\n\tOctubre: 93" + "\n\tNoviembre: 63" + "\n\tDiciembre: 43" + "\n  RelacionMesPico: Abril"
					+ "\n  ContadorCambiosPico: 0,17" + "\n  RatioActividadCambio: 12,82" + "\n  CommitPorDia: "
					+ "\n\tDomingo: 197" + "\n\tLunes: 137" + "\n\tMartes: 146" + "\n\tMiercoles: 135"
					+ "\n\tJueves: 165" + "\n\tViernes: 131" + "\n\tSabado: 153" + "\n  CambioPorAutor: "
					+ "\n\tSHIBATA Hiroshi: 132" + "\n\tCameron Savage: 1" + "\n\tAzkar Moulana: 4"
					+ "\n\tHiroyuki Morita: 1" + "\n\tVesa Vänskä: 166" + "\n\tPeter Berkenbosch: 1" + "\n\tOlga: 1"
					+ "\n\tMayumi EMORI: 5" + "\n\tMax Mulatz: 2" + "\n\tAndreas Krüger: 3" + "\n\tMarkus Krogemann: 1"
					+ "\n\tKarin Hendrikse: 3" + "\n\tElle Meredith: 1" + "\n\tFloor Drees: 36" + "\n\tkhendrikse: 3"
					+ "\n\tOlle Jonsson: 1" + "\n\tOlia Kremmyda: 3" + "\n\tFloorD: 2" + "\n\tEito Katagiri: 15"
					+ "\n\tMayra Cabrera: 2" + "\n\tLena Plaksina: 1" + "\n\tMarius Nünnerich: 1"
					+ "\n\tBarbara Barbosa: 1" + "\n\tBeatriz: 1" + "\n\tAna Schwendler: 8" + "\n\tBenedikt Deicke: 1"
					+ "\n\tKlaus Zanders: 2" + "\n\tAlexia McDonald: 1" + "\n\tmignonnesaurus: 2"
					+ "\n\tMadeleine Neumann: 2" + "\n\tLiane: 1" + "\n\tIvan Malykh: 2"
					+ "\n\tWillian van der Velde: 3" + "\n\talicetragedy: 6" + "\n\tRemon Oldenbeuving: 1"
					+ "\n\twillian: 5" + "\n\tLilly Piri: 1" + "\n\tKuniaki IGARASHI: 51" + "\n\tMichalina: 1"
					+ "\n\tInbal Ghilai: 1" + "\n\tInbal Gilai: 1" + "\n\theisenburger: 2" + "\n\tSidney Liebrand: 5"
					+ "\n\tMatthijs: 1" + "\n\tAdam Niedzielski: 4" + "\n\tAlex Jahraus: 2" + "\n\tAngelos Orfanakos: 6"
					+ "\n\tAlexander Jahraus: 1" + "\n\tAlex Tsiras: 1" + "\n\tELI JOSEPH BRADLEY: 1"
					+ "\n\tEli Joesph Bradley: 1" + "\n\tNina Siessegger: 3" + "\n\ttitanoboa: 6"
					+ "\n\tMauro Otonelli: 1" + "\n\tThibaud Colas: 1" + "\n\tLaura: 5" + "\n\tRichard Baptist: 3"
					+ "\n\tLiesbethW: 1" + "\n\tAkihiro Sada: 9" + "\n\tKatherine Wu: 1" + "\n\tTerence Lee: 33"
					+ "\n\tJuanitoFatas: 2" + "\n\tRasmus Kjellberg: 2" + "\n\tAlyssa Pohahau: 1" + "\n\tastux7: 1"
					+ "\n\tkanizmb: 5" + "\n\tElliott Hilaire: 5" + "\n\tKite: 1" + "\n\tJason Salaz: 1"
					+ "\n\tJalyna: 7" + "\n\tJoe Corcoran: 1" + "\n\tGloria Dwomoh: 2" + "\n\tAndrew Harvey: 1"
					+ "\n\tGregory McIntyre: 4" + "\n\tJoshua Paling: 5" + "\n\tAmy Simmons: 1" + "\n\tOlga Matoula: 1"
					+ "\n\tNataly Tkachuk: 1" + "\n\tJohn Gabriel: 1" + "\n\tLucy Bain: 15" + "\n\tTracy Mu Sung: 1"
					+ "\n\tstephfz: 7" + "\n\tNick Charlton: 1" + "\n\tAki: 5" + "\n\tMei Brough-Smyth: 1"
					+ "\n\tEd Drain: 9" + "\n\tKaren Da Cruz: 16" + "\n\tJhoon Saravia: 5" + "\n\tPiotr Szotkowski: 5"
					+ "\n\tbartuz: 16" + "\n\tAkshay Karle: 1" + "\n\tAkira Matsuda: 1" + "\n\tDaniel Puglisi: 6"
					+ "\n\tDimitar Dimitrov: 7" + "\n\tJuha-Matti: 2" + "\n\tJuha-Matti Santala: 2"
					+ "\n\tBrigitte Jellinek: 1" + "\n\tMyriam Leggieri: 5" + "\n\tPeter Armstrong: 3"
					+ "\n\tIlona Shub: 2" + "\n\tJuanito Fatas: 10" + "\n\tDavid Carlin: 2" + "\n\tKrystal Fister: 1"
					+ "\n\tRichard Schneeman: 1" + "\n\tTracyMu: 1" + "\n\tschneems: 6" + "\n\ttracymu: 2"
					+ "\n\tBrittany Martin: 1" + "\n\tLinda: 50" + "\n\tEloy Durán: 3" + "\n\t5minpause: 1"
					+ "\n\tCarmenPalMar: 1" + "\n\tTatsuya Ogi: 1" + "\n\tdividead: 1" + "\n\tChris Frederick: 1"
					+ "\n\tAnika: 1" + "\n\tYuta Kurotaki: 1" + "\n\tNicola Hauke: 1" + "\n\tAlja Isakovic: 8"
					+ "\n\tKatie Miller: 13" + "\n\tbethesque: 1" + "\n\t₍˄ุ.͡˳̫.˄ุ₎: 2" + "\n\tWinston: 1"
					+ "\n\tJosef Šimánek: 1" + "\n\tJonathan Reyes: 1" + "\n\tIrina Dumitrascu: 3"
					+ "\n\tTzu-ping Chung: 2" + "\n\tRyan Koehler: 1" + "\n\tjudges119: 3" + "\n\tnekova: 1"
					+ "\n\twhatwho: 3" + "\n\tZac: 1" + "\n\tDaniel Gruenthal: 1" + "\n\tBassam Ismail: 1"
					+ "\n\tLaura Lindeman: 1" + "\n\tMerrin: 1" + "\n\tRyudo Awaru: 2" + "\n\t唐鳳: 1" + "\n\tsareg0: 1"
					+ "\n\tMichael Kirk: 1" + "\n\tKasia Jarmołkowicz: 2" + "\n\tBen Maraney: 4" + "\n\ttheadactyl: 2"
					+ "\n\tNiu Yameng: 3" + "\n\tSho Hashimoto: 5" + "\n\tcatherine: 3" + "\n\tKenta Murata: 2"
					+ "\n\tAwaru Ryudo: 2" + "\n\tRisa Batta: 6" + "\n\tClaudio Ortolina: 1" + "\n\tKatja Zorina: 2"
					+ "\n\tAntti Salonen: 2" + "\n\tviddity: 1" + "\n\tVerena Brodbeck: 1" + "\n\tThijs Cadier: 1"
					+ "\n\tPatrick Baselier: 1" + "\n\tDaniel Harcek: 1" + "\n\tmermop: 1" + "\n\tMerrin Macleod: 3"
					+ "\n\tMatthew Savage: 6" + "\n\tImogen Wentworth: 4" + "\n\tRyunosuke SATO: 2" + "\n\tstephanie: 1"
					+ "\n\tLinda Liukas: 4" + "\n\tJen Diamond: 1" + "\n\tRachel Graves: 2" + "\n\tSteve Klabnik: 3"
					+ "\n\tKerstin Kollmann: 1" + "\n\tVivian Li: 1" + "\n\tsylvain.abelard+git@gmail.com: 1"
					+ "\n\tAlyson La: 20" + "\n\tJoakim Runeberg: 1" + "\n\tangelito rojas: 5" + "\n\tAndy Lindeman: 2"
					+ "\n\tMary Jenn: 10" + "\n\tJennifer Coryell: 1" + "\n\tHenrietta: 3" + "\n\tLevent Ali: 2"
					+ "\n\tPhil Nash: 1" + "\n\tJordan McCullough: 1" + "\n\tVesa Vänskä: 2"
					+ "\n\tErnesto Jiménez: 7" + "\n\tGenadi Samokovarov: 1" + "\n\tAndrew Nesbitt: 7" + "\n\tialja: 1"
					+ "\n\tPatrick Davey: 1" + "\n\tsundevilyang: 1" + "\n\tlsedaghat: 2" + "\n\tJessica Allen: 1"
					+ "\n\talexliao: 2" + "\n\tDanish Khan: 1" + "\n\tPatrik Ragnarsson: 1" + "\n\tPiotr Steininger: 1"
					+ "\n\tMika Marttila: 1" + "\n\tweidenfreak: 2" + "\n\tFelix Schäfer: 1" + "\n\tNorbert Crombach: 5"
					+ "\n\tKarri Saarinen: 35" + "\n\tKonstantin Haase: 1" + "\n\tSteven! Ragnarök: 1"
					+ "\n\tMiha Filej: 3" + "\n\tTimo H: 2" + "\n\tMatias Korhonen: 6" + "\n\tVille Lautanala: 1"
					+ "\n\tJanika: 2" + "\n\tYassine Zenati: 1" + "\n\tAlex Coles: 5" + "\n\tBen Orenstein: 2"
					+ "\n\tConsti: 2" + "\n  ContadorAutor: 0,20" + "\n  IssuesPorAutor: " + "\n\tcsavage5: 1"
					+ "\n\tazkarmoulana: 1" + "\n\thsbt: 9" + "\n\tchiastolite: 1" + "\n\tcoldwell: 1"
					+ "\n\tpeterberkenbosch: 1" + "\n\tmvrozanti: 1" + "\n\tsalbertson: 1" + "\n\tshayanealcantara: 1"
					+ "\n\tolgarithms: 2" + "\n\tfakenine: 1" + "\n\temorima: 4" + "\n\tMuhammetDilmac: 1"
					+ "\n\tklappradla: 1" + "\n\taknrdureegaesr: 3" + "\n\tmkrogemann: 2" + "\n\tvarotrix: 1"
					+ "\n\tkhendrikse: 3" + "\n\titsmesarthak: 1" + "\n\tyoshiokatsuneo: 1" + "\n\tsunsh1274: 1"
					+ "\n\tlotsathought: 1" + "\n\tAndreasHassing: 1" + "\n\tanaschwendler: 5" + "\n\tivdma: 1"
					+ "\n\tolleolleolle: 2" + "\n\toliakremmyda: 3" + "\n\tFloorD: 13" + "\n\tmsorre2: 1"
					+ "\n\tanaufalm: 1" + "\n\tSaichethan: 1" + "\n\tmayra-cabrera: 1" + "\n\tlena-pl: 1"
					+ "\n\tmarius: 1" + "\n\tbahbbc: 1" + "\n\tbeatrizrezener: 1" + "\n\tklaustopher: 1"
					+ "\n\tmignonnesaurus: 1" + "\n\tmadeleine-neumann: 2" + "\n\talicetragedy: 3" + "\n\tRSO: 1"
					+ "\n\tWillianvdv: 3" + "\n\tlillypiri: 2" + "\n\tigaiga: 14" + "\n\tmichalina: 2" + "\n\tinbalg: 2"
					+ "\n\tmatthijsvdr: 1" + "\n\tSidOfc: 2" + "\n\tadamniedzielski: 5" + "\n\tfanaugen: 3"
					+ "\n\tagorf: 1" + "\n\tfabmann: 1" + "\n\tarialblack14: 1" + "\n\tEtherTyper: 2"
					+ "\n\ttitanoboa: 4" + "\n\tsssggr: 2" + "\n\tmauro-oto: 1" + "\n\tthibaudcolas: 1"
					+ "\n\terikacb: 1" + "\n\tbfmjr: 1" + "\n\tandersonfernandes: 1" + "\n\tLiesbethW: 1"
					+ "\n\trpbaptist: 1" + "\n\tsada: 2" + "\n\tkwugirl: 1" + "\n\tJuanitoFatas: 13"
					+ "\n\tkjellberg: 2" + "\n\tChidoriY: 1" + "\n\talyssa: 1" + "\n\tastux7: 1" + "\n\tkanizmb: 4"
					+ "\n\telliotthilaire: 5" + "\n\tmeagan7: 1" + "\n\tixkaito: 1" + "\n\tVxJasonxV: 1"
					+ "\n\tjalyna: 7" + "\n\tjoecorcoran: 1" + "\n\tblossomica: 1" + "\n\tpuyo: 3" + "\n\tamysimmons: 1"
					+ "\n\ttnataly: 1" + "\n\telifkus: 1" + "\n\tjoaumg: 1" + "\n\ttracymu: 3" + "\n\tvesan: 1"
					+ "\n\tstephfz: 3" + "\n\tnickcharlton: 1" + "\n\tleifg: 1" + "\n\tmelatonind: 1"
					+ "\n\tSoldierCoder: 5" + "\n\tBartuz: 2" + "\n\takshaykarle: 1" + "\n\tamatsuda: 1"
					+ "\n\tdanielpuglisi: 2" + "\n\tlbain: 3" + "\n\tkeikoro: 2" + "\n\tHamatti: 2" + "\n\tbjelline: 1"
					+ "\n\ttamouse: 1" + "\n\tpeteretep: 2" + "\n\tghost: 3" + "\n\tdeclanatticus: 1"
					+ "\n\tilonashub: 1" + "\n\tlindaliukas: 1" + "\n\tdavich: 1" + "\n\tkrystalmaria: 1"
					+ "\n\tMagdaMalinowska: 1" + "\n\tschneems: 6" + "\n\twonderwoman13: 1" + "\n\tFeather130: 1"
					+ "\n\t18847172204: 1" + "\n\talloy: 1" + "\n\t5minpause: 1" + "\n\tCarmenPalMar: 2"
					+ "\n\tt-oginogin: 1" + "\n\tdividead: 1" + "\n\tkansaichris: 1" + "\n\tchastell: 1"
					+ "\n\tkurotaky: 1" + "\n\tjasinai: 1" + "\n\tialja: 5" + "\n\tkatiejots: 5" + "\n\tandreionut: 1"
					+ "\n\tbethesque: 2" + "\n\tjina: 2" + "\n\tsimi: 1" + "\n\tjreyes33: 1" + "\n\tdira: 6"
					+ "\n\turanusjr: 1" + "\n\tryanjkoehler: 1" + "\n\tnekova: 1" + "\n\twhatwho: 2"
					+ "\n\tjudges119: 1" + "\n\txanhast: 1" + "\n\tcroaker: 1" + "\n\tskippednote: 1" + "\n\tlukefx: 1"
					+ "\n\tlclindeman: 1" + "\n\tfishkingsin: 1" + "\n\tshicholas: 1" + "\n\tmarcdel: 1"
					+ "\n\taudreyt: 1" + "\n\tmitio: 3" + "\n\teitoball: 11" + "\n\tmichaelkirk: 1" + "\n\tidengager: 1"
					+ "\n\tBen-M: 2" + "\n\ttheadactyl: 2" + "\n\tmengmeng0927: 1" + "\n\trotev: 4"
					+ "\n\tcatherine-jones: 2" + "\n\tmrkn: 2" + "\n\tsho-h: 3" + "\n\trbatta: 1"
					+ "\n\ternesto-jimenez: 2" + "\n\ttomdev: 1" + "\n\tcloud8421: 1" + "\n\tantsa: 1"
					+ "\n\tviddity: 1" + "\n\tthijsc: 1" + "\n\tbazzel: 1" + "\n\tharcek: 1" + "\n\tgirijabrahme: 1"
					+ "\n\tmermop: 1" + "\n\tamasses: 1" + "\n\tnegomi: 3" + "\n\ttricknotes: 2" + "\n\tabelards: 4"
					+ "\n\tjendiamond: 1" + "\n\tdidlix: 1" + "\n\tsteveklabnik: 3" + "\n\ttechpeace: 1"
					+ "\n\tviv-li: 1" + "\n\teoy: 1" + "\n\thahahana: 2" + "\n\tyoshihara: 1" + "\n\talysonla: 1"
					+ "\n\tmfjenn: 2" + "\n\talindeman: 1" + "\n\tdasmoose: 1" + "\n\tlevent: 1" + "\n\tphilnash: 1"
					+ "\n\tgsamokovarov: 1" + "\n\tandrew: 6" + "\n\tpatrickdavey: 1" + "\n\thone: 4"
					+ "\n\tlsedaghat: 2" + "\n\tspacekat: 1" + "\n\talexliao: 1" + "\n\tdanishkhan: 1"
					+ "\n\tnorbert: 4" + "\n\tdentarg: 1" + "\n\tpsteininger: 1" + "\n\taom: 1" + "\n\tMacroz: 1"
					+ "\n\tweidenfreak: 4" + "\n\tthegcat: 1" + "\n\tafknapping: 1" + "\n\trkh: 1" + "\n\tbtuduri: 1"
					+ "\n\tnuclearsandwich: 1" + "\n\tmatiaskorhonen: 4" + "\n\tmfilej: 2" + "\n\tksaa: 1"
					+ "\n\ttimoh: 1" + "\n\tlautis: 1" + "\n\tjanika: 1" + "\n\tmyabc: 2" + "\n\tr00k: 2"
					+ "\n\tconsti: 1" + "\n  NumeroFavoritos: 950";

			assertEquals(cadena, resultadoMetricas[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}