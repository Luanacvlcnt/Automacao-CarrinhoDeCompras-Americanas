package tests;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ConsultPage {

    private WebDriver navegador;
    private String textoElemento;

    @BeforeTest
    private void setUp() {
        //Abrindo o navegador
        System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
        navegador = new ChromeDriver();
        navegador.manage().window().maximize();
        navegador.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        //Navegando para a pagina americanas
        navegador.get("https://www.americanas.com.br/");
    }


    @Test(priority = 1)
    private void testBuscarConsolePs4() {

        //Digitar no campo com name "conteudo" o texto "console ps4"
        navegador.findElement(By.id("h_search-input")).sendKeys("console ps4");

        //Clicar no botão de busca com id "h_search-btn"
        navegador.findElement(By.id("h_search-btn")).click();
    }

    @Test(priority = 2)
    private void selecionarProduto() {

        //Identificando o texto do elemento e Clicando no primeiro resultado "console Ps4" através do seu xpath
        By produto = By.xpath("//*[@id=\"rsyswpsdk\"]/div/main/div/div[3]/div[2]/div[1]/div/div/a/span[1]");
        aguardarElementoVisivel(produto);
        scrollClique(produto);

        WebElement textoProduto = navegador.findElement(By.xpath("//*[@id=\"rsyswpsdk\"]/div/main/div[2]/div[1]/div[2]/span"));
        textoElemento = textoProduto.getText();

    }

    @Test(priority = 3)
    private void informarCep() {
        //Digitar o cep "51230360" no campo cep
        navegador.findElement(By.name("cep")).sendKeys("51230360");

        //rolar scroll para baixo e clicar no botao "ok"
        scrollClique(By.xpath("//*[@id=\"rsyswpsdk\"]/div/main/div[2]/div[2]/div[2]/form/div/div[2]/button"));
    }

    @Test(priority = 4)
    private void incluirNoCarrinho() {
        //Clicar no botão com texto "comprar"
        navegador.findElement(By.linkText("comprar")).click();

        //Selecionar garantia de 12 meses
        navegador.findElement(By.xpath("//*[@id=\"rsyswpsdk\"]/div/main/div[2]/div[1]/div/div[2]/div/div[2]/div/div[2]/div[1]/div[1]/div/div")).click();

        //Baixar o scroll e clicar no botão "continuar"
        scrollClique(By.linkText("continuar"));
    }

    @Test(priority = 5)
    private void validacaoProduto() {
        //Validar se o produto se encontra na cesta
        aguardarElementoVisivel(By.xpath("//*[@id=\"root\"]/div/section/div/article/div[2]/a/h3"));
        WebElement TenhoOProdutoIncluidoNoCarrinho = navegador.findElement(By.xpath("//*[@id=\"root\"]/div/section/div/article/div[2]/a/h3"));
        String nomeProduto = TenhoOProdutoIncluidoNoCarrinho.getText();
        Assert.assertEquals(textoElemento, nomeProduto);
    }

    //Metodos para rolar scroll e clicar no elemento
    private void scrollClique(By element) {
        WebElement ele = navegador.findElement(element);
        ((JavascriptExecutor) navegador).executeScript("arguments[0].scrollIntoView(true);",
                navegador.findElement(element));
        JavascriptExecutor executor = (JavascriptExecutor) navegador;
        executor.executeScript("arguments[0].click();", ele);
    }
    //Metodo para aguardar elemento visivel na pagina
    private void aguardarElementoVisivel(By element) {
        WebDriverWait wait = new WebDriverWait(navegador, 10);
        wait.until(ExpectedConditions.visibilityOf(navegador.findElement(element)));
    }

    //Ao finalizar os testes irá fechar o navegador
    @AfterTest
    private void tearDown() {

        // Fechar o navegador
        navegador.quit();
    }
}
