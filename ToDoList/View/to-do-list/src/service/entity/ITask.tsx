export interface ITask {
    id : number;
    titulo : string;
    description : string;
    complete : boolean;
    createdAt : Date;
    dataFim : Date;
    status : string;
    prazo : number;
    prioridade : string;
    tipoTarefa : string;
    taskLivre : boolean;
}

export enum StatusEnum {
    TAREFA = 'TAREFA',
    INICIALIZADA = 'INICIALIZADA',
    FINALIZADA = 'FINALIZADA',
}