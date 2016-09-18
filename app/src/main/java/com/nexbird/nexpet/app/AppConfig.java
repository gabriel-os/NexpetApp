package com.nexbird.nexpet.app;

public class AppConfig {

    public static String URL_GLOBAL = "nexpet.esy.es";

    public static String SW = "http://" + URL_GLOBAL + "/";

    public static String URL_LOGIN = SW + "login.php";

    public static String URL_REGISTER = SW + "register.php";

    public static String URL_RECUPERA = SW + "sendEmail.php";

    public static String URL_RECUPERA_AGENDADO = SW + "recoveryScheduled.php";

    public static String URL_RECUPERA_AGENDAR = SW + "recoverySchedule.php";

    public static String URL_VERIFICA_DATAHORA = SW + "checkDateTime.php";

    public static String URL_ARMAZENA_AGENDAMENTO = SW + "storeScheduling.php";

    public static String URL_ALTERA_DADOS = SW + "changeData.php";

    public static String URL_TEMPO_SERVICO = SW + "getDateHour.php";

}
