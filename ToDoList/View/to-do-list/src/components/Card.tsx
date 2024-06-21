import React, { useState, useEffect } from 'react';
import { FaEdit, FaTrash, FaExclamationTriangle } from 'react-icons/fa';
import EditModal from './EditModal.tsx';
import { ITask } from '../service/entity/ITask';
import { deleteTasks } from '../service/index.tsx';
import { TaskTypes } from '../enums/taskTypes.tsx';

const Card = ({ tasks }: { tasks: ITask[] }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedTask, setSelectedTask] = useState<ITask | null>(null);
    const [loading, setLoading] = useState(false);
    const [deleted, setDeleted] = useState(false);
    const [showAlert, setShowAlert] = useState(false);
    const [progress, setProgress] = useState(100); // Inicia com 100%

    useEffect(() => {
        let interval: NodeJS.Timeout;
        if (showAlert) {
            // Reduz a barra de progresso de 100% para 0% em 2 segundos
            const decreaseProgress = () => {
                interval = setInterval(() => {
                    setProgress(prev => prev - 1); // Reduz 1% a cada 10ms
                }, 20); // Intervalo de 20ms
            };

            decreaseProgress();

            // Após 2 segundos, recarrega a página
            setTimeout(() => {
                reloadPage();
            }, 2000);
        }

        return () => clearInterval(interval);
    }, [showAlert]);

    const handleEditClick = (task: ITask) => {
        setSelectedTask(task);
        setIsModalOpen(true);
    };

    const handleDeleteClick = async (id: number) => {
        setLoading(true);
        try {
            // Simular uma requisição de deleção (substitua com sua lógica real de deleção)
            await deleteTasks(id);
            setDeleted(true);
            setShowAlert(true);
        } catch (error) {
            setDeleted(false);
        } finally {
            setLoading(false);
        }
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setSelectedTask(null);
    };
    const getPriorityHoverColor = (priority: string) => {
        switch (priority) {
            case 'ALTA':
                return '#FF0000';
            case 'MEDIA':
                return '#FFD700';
            case 'BAIXA':
                return '#008000';
            default:
                return '#ccc';
        }
    };
    const formatDate = (dateString: string): string => {
      const date = new Date(dateString);
      const localDate = new Date(date.getTime() + date.getTimezoneOffset() * 60000);
      const day = String(localDate.getDate()).padStart(2, '0');
      const month = String(localDate.getMonth() + 1).padStart(2, '0'); // Janeiro é 0!
      const year = localDate.getFullYear();
      return `${day}/${month}/${year}`;
    };


    const calculateDaysRemaining = (dateString: string): number => {
      const currentDate = new Date();
      const targetDate = new Date(dateString);
      const differenceInMillis = targetDate.getTime() - currentDate.getTime();
      const differenceInDays = Math.ceil(differenceInMillis / (1000 * 60 * 60 * 24));
      return differenceInDays;
    };
    const setColorDate = (dateString: string): string => {
        if(calculateDaysRemaining(dateString) <= 2){
          return 'red';
        }
    };

    const reloadPage = () => {
        window.location.reload();
    };

    return (
        <div>
            <div style={{ position: 'relative' }}>
                {tasks.map(task => (
                    <div
                        key={task.id}
                        style={{
                            position: 'relative',
                            margin: '10px 0',
                            padding: '10px',
                            border: '1px solid #ccc',
                            borderRadius: '5px',
                            backgroundColor: '#fff',
                            cursor: 'pointer',
                            transition: 'border-color 0.3s'
                        }}
                        onClick={() => handleEditClick(task)}
                        onMouseEnter={(e) => e.currentTarget.style.borderColor = getPriorityHoverColor(task.prioridade)}
                        onMouseLeave={(e) => e.currentTarget.style.borderColor = '#ccc'}
                    >
                        <div style={{ position: 'absolute', top: '10px', left: '10px', display: 'flex', alignItems: 'center' }}>
                            <h4 style={{ margin: 0 }}>{task.titulo}</h4>
                            <span style={{ marginLeft: '10px', fontSize: '0.8em', color: getPriorityHoverColor(task.prioridade) }}>{task.prioridade}</span>

                        {task.tipoTarefa === TaskTypes.DATA && (
                            <span style={{ marginLeft: '240px', color: setColorDate(task.dataFim)}}>
                              {formatDate(task.dataFim)}
                            </span>
                        )}
                          {task.tipoTarefa === TaskTypes.DIAS && (
                            <span style={{ marginLeft: '260px', color: setColorDate(task.dataFim)}}>
                              {calculateDaysRemaining(task.dataFim)} dias
                            </span>
                        )}
                        </div>
                        <p style={{marginTop : '20px'}}>{task.description}</p>
                        <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '10px' }}>
                            <FaTrash onClick={(e) => {
                                e.stopPropagation();
                                handleDeleteClick(task.id);
                            }} style={{ cursor: 'pointer' }} />
                        </div>
                    </div>
                ))}
                {loading && (
                    <div style={{
                        position: 'fixed',
                        top: 0,
                        left: 0,
                        width: '100%',
                        height: '100%',
                        backgroundColor: 'rgba(255, 255, 255, 0.5)', // Overlay semitransparente
                        zIndex: 1000,
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center'
                    }}>
                        <p>Deletando tarefa...</p>
                    </div>
                )}
                {showAlert && (
                    <div style={{
                        position: 'fixed',
                        top: 0,
                        left: 0,
                        width: '100%',
                        height: '100%',
                        backgroundColor: 'rgba(0, 0, 0, 0.5)',
                        zIndex: 1000,
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center'
                    }}>
                        <div style={{
                            width: '100%',
                            maxWidth: '600px',
                            backgroundColor: '#fff',
                            color: '#000',
                            borderRadius: '5px',
                            padding: '20px',
                            boxShadow: '0px 0px 10px rgba(0,0,0,0.1)',
                            display: 'flex',
                            alignItems: 'center',
                            flexDirection: 'column'
                        }}>
                            <div style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
                                <FaExclamationTriangle style={{ marginRight: '10px', color: 'red', fontSize: '1.5em' }} />
                                <div style={{  width: '1px', height: '20px', backgroundColor: '#ccc', marginRight: '10px' }}></div>
                                <div>
                                    <div style={{
                                        width: '100%',
                                        height: '5px',
                                        backgroundColor: 'red',
                                        marginBottom: '5px',
                                        overflow: 'hidden'
                                    }}>
                                        <div style={{
                                            height: '100%',
                                            width: '100%',
                                            backgroundColor: 'red',
                                        }} />
                                    </div>
                                    <p style={{ textAlign: 'center' }}>Tarefa deletada com sucesso!</p>
                                </div>
                            </div>
                        </div>
                    </div>
                )}
            </div>
            {isModalOpen && selectedTask && (
                <div style={{
                    position: 'fixed',
                    top: 0,
                    left: 0,
                    right: 0,
                    bottom: 0,
                    display: 'flex',
                    zIndex: 1000,
                    borderRadius: '5px',
                }}>
                    <EditModal
                        card={selectedTask}
                        onClose={handleCloseModal}
                    />
                </div>
            )}
            {isModalOpen && <div style={{
                position: 'fixed',
                top: 0,
                left: 0,
                width: '100%',
                height: '100%',
                backgroundColor: 'transparent',
                zIndex: 999
            }} />}
        </div>
    );
};

export default Card;
