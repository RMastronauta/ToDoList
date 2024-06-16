import Column from './Column.tsx';
import {getTasks} from "../service/index.tsx";
import { useState, useEffect } from "react";
import {ITask} from "../service/entity/ITask";

const Board = () => {
    const statuses = [ 'BACKLOG', 'INICIALIZADA', 'FINALIZADA' ];
    const [resultado, setResultado] = useState<ITask[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await getTasks();
                setResultado(response);
            } catch (error) {
                console.error('Erro ao buscar tarefas:', error);
                // Tratar erros conforme necessário
            }
        };

        fetchData();
    }, []);
    console.log(resultado);
    return (
        <div style={{ display: 'flex', justifyContent: 'space-between', padding: '20px' }}>
            {statuses.map((status) => (
                <Column key={status} task={resultado.filter(x => x.status === status)} status={status} />
            ))}
        </div>
    );
};

export default Board;
