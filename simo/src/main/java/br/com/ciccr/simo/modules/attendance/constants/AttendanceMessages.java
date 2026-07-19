package br.com.ciccr.simo.modules.attendance.constants;

public final class AttendanceMessages {

    private AttendanceMessages() {}

    public static final String CREATED =
            "Atendimento criado com sucesso.";

    public static final String UPDATED =
            "Atendimento atualizado com sucesso.";

    public static final String CLOSED =
            "Atendimento encerrado com sucesso.";

    public static final String REOPENED =
            "Atendimento reaberto com sucesso.";

    public static final String FOUND =
            "Atendimento encontrado.";

    public static final String LIST =
            "Atendimentos encontrados.";

    public static final String NOT_FOUND =
            "Atendimento não encontrado.";

    public static final String ALREADY_CLOSED =
            "O atendimento já está encerrado.";

    public static final String NOT_CLOSED =
            "O atendimento não está encerrado.";

    public static final String PROTOCOL_ALREADY_EXISTS =
            "Já existe um atendimento com este protocolo.";

    public static final String CANCELED_CANNOT_BE_UPDATED =
            "Um atendimento cancelado não pode ser alterado.";

    public static final String SNAPSHOT_ERROR =
            "Erro ao gerar histórico do atendimento.";
}