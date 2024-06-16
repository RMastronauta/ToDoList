import { useState } from 'react';
import {FaCheckCircle, FaTimes} from 'react-icons/fa';
// @ts-ignore
import { TaskTypes } from '../enums/taskTypes.tsx';
import {onAdvanceTask, postTask} from '../service/index.tsx';
import {TaskStatus} from "../enums/taskStatus.tsx";

const EditModal = ({ card, onClose }) => {
    const [titulo, setTitulo] = useState(card.titulo);
    const [description, setDescription] = useState(card.description);
    const [taskType, setTaskType] = useState(card.tipoTarefa);
    const [endDate, setEndDate] = useState(card.dataFim || '');
    const [days, setDays] = useState(card.days || '');
    const [priority, setPriority] = useState(card.prioridade);
    const [status, setStatus] = useState(card.status);
    const [isLoading, setIsLoading] = useState(false);
    const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    const [showErrorMessage, setShowErrorMessage] = useState(false);
    const [mensagemSucesso, setMensagemSucesso] = useState('');
    const [mensagemErro, setMensagemErro] = useState('');

    const handleSaveClick = async () => {
        setIsLoading(true);
        try {
            const data = {
                id: card.id,
                titulo: titulo,
                description: description,
                complete: false,
                createdAt: card.createdAt, // Manter a data de criação original
                dataFim: taskType === TaskTypes.DATA ? endDate : null,
                taskLivre: taskType === TaskTypes.LIVRE,
                prazo: taskType === TaskTypes.DIAS ? days : null,
                prioridade: priority,
                tipoTarefa: getTaskTypeValue(taskType),
                status: status
            };
            debugger
            const response = await postTask(data);

            if (response.id > 0) {
                setShowSuccessMessage(true);
                setMensagemSucesso('Tarefa editada com sucesso!');
            } else {
                throw new Error('Erro ao salvar a tarefa');
            }
        } catch (error) {
            setShowErrorMessage(true);
            setMensagemErro('Erro ao salvar a tarefa: ' + error);
        } finally {
            setTimeout(() => {
                setShowSuccessMessage(false);
                onClose();
                window.location.reload();
            }, 3000);
            setIsLoading(false);
        }
    };
    const onAdvance = async (id : number) => {
        setIsLoading(true);
        try {
            const response = await onAdvanceTask(id, getTaskStatusValue(status) + 1);

            if (response.id > 0) {
                setShowSuccessMessage(true);
                setMensagemSucesso('A tarefa avançou para ' + response.status + ' com sucesso!');
            } else {
                throw new Error('Erro ao avançar a tarefa');
            }
        } catch (error) {
            setShowErrorMessage(true);
            setMensagemErro('Erro ao avançar a tarefa: ' + error);
        } finally {
            setTimeout(() => {
                setShowSuccessMessage(false);
                onClose();
                window.location.reload();
            }, 3000);
            setIsLoading(false);
        }
    };
    const onRecede = async (id : number) => {
        setIsLoading(true);
        try {
            const response = await onAdvanceTask(id, getTaskStatusValue(status) - 1);

            if (response.id > 0) {
                setShowSuccessMessage(true);
                setMensagemSucesso('A tarefa retornou para ' + response.status + ' com sucesso!');
            } else {
                throw new Error('Erro ao retroceder a tarefa');
            }
        } catch (error) {
            setShowErrorMessage(true);
            setMensagemErro('Erro ao retroceder a tarefa: ' + error);
        } finally {
            setTimeout(() => {
                setShowSuccessMessage(false);
                onClose();
                window.location.reload();
            }, 3000);
            setIsLoading(false);
        }
    };
    const getTaskTypeValue = (taskType) => {
        switch (taskType) {
            case TaskTypes.DATA:
                return 0;
            case TaskTypes.DIAS:
                return 1;
            case TaskTypes.LIVRE:
                return 2;
            default:
                return null;
        }
    };
    const getTaskStatusValue = (status) => {
        switch (status) {
            case TaskStatus.BACKLOG:
                return 0;
            case TaskStatus.INICIALIZADA:
                return 1;
            case TaskStatus.FINALIZADA:
                return 2;
            default:
                return null;
        }
    };
    return (
        <div style={styles.modalOverlay}>
            <div style={styles.modalContent}>
                <div style={styles.modalHeader}>
                    <h2>Editar Tarefa</h2>
                    <FaTimes onClick={onClose} style={styles.closeIcon} />
                </div>
                <div style={styles.modalBody}>
                    <div style={styles.formGroup}>
                        <label>Título</label>
                        <input
                            type="text"
                            value={titulo}
                            onChange={(e) => setTitulo(e.target.value)}
                            style={styles.titleInput}
                        />
                    </div>
                    <div style={styles.formGroup}>
                        <label>Descrição</label>
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            rows={4}
                            style={styles.textarea}
                        />
                    </div>
                    <div style={styles.formGroup}>
                        <label>Status</label>
                        <div style={styles.status}>{card.status}</div>
                    </div>
                    <div style={styles.formGroup}>
                        <label>Tipo de Tarefa</label>
                        <select
                            value={taskType}
                            onChange={(e) => setTaskType(e.target.value)}
                            style={styles.input}
                        >
                            <option value={TaskTypes.DATA}>Por Data</option>
                            <option value={TaskTypes.DIAS}>Por Dias</option>
                            <option value={TaskTypes.LIVRE}>Livre</option>
                        </select>
                    </div>
                    {taskType === TaskTypes.DATA && (
                        <div style={styles.formGroup}>
                            <label>Data de Término</label>
                            <input
                                type="date"
                                value={endDate}
                                onChange={(e) => setEndDate(e.target.value)}
                                style={styles.input}
                            />
                        </div>
                    )}
                    {taskType === TaskTypes.DIAS && (
                        <div style={styles.formGroup}>
                            <label>Dias para Completar</label>
                            <input
                                type="number"
                                value={days}
                                onChange={(e) => setDays(e.target.value)}
                                style={styles.input}
                            />
                        </div>
                    )}
                    <div style={styles.formGroup}>
                        <label>Prioridade</label>
                        <select
                            value={priority}
                            onChange={(e) => setPriority(e.target.value)}
                            style={styles.input}
                        >
                            <option value="ALTA">ALTA</option>
                            <option value="MEDIA">MÉDIA</option>
                            <option value="BAIXA">BAIXA</option>
                        </select>
                    </div>
                    {showSuccessMessage && (
                        <div style={styles.successMessage}>
                            <FaCheckCircle style={styles.successIcon} />
                            <span>{mensagemSucesso}</span>
                        </div>
                    )}
                    {showErrorMessage && (
                        <div style={styles.errorMessage}>
                            {mensagemErro} + "Tente novamente!"
                        </div>
                    )}
                </div>
                <div style={styles.modalFooter}>
                    <button onClick={handleSaveClick} style={styles.saveButton} disabled={isLoading}>
                        {isLoading ? 'Salvando...' : 'Salvar'}
                    </button>
                    {getTaskStatusValue(card.status) === 0 && (
                        <button onClick={() => onAdvance(card.id)} style={styles.advanceButton}>Avançar</button>
                    )}
                    {getTaskStatusValue(card.status) === 1 && (
                        <div>
                            <button onClick={() => onRecede(card.id)} style={styles.recedeButton}>Retroceder</button>

                            <button onClick={() => onAdvance(card.id)} style={styles.advanceButton}>Avançar</button>
                        </div>
                    )}
                    {getTaskStatusValue(card.status) === 2 && (
                        <button onClick={() => onRecede(card.id)} style={styles.recedeButton}>Retroceder</button>
                    )}
                </div>
            </div>
        </div>
    );
};

const styles = {
    modalOverlay: {
        position: 'fixed',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
    },
    modalContent: {
        background: '#fff',
        padding: '30px',
        borderRadius: '10px',
        width: '600px',
        boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
    },
    modalHeader: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderBottom: '1px solid #eee',
        paddingBottom: '10px',
        marginBottom: '20px',
    },
    closeIcon: {
        cursor: 'pointer',
    },
    modalBody: {
        marginBottom: '20px',
    },
    formGroup: {
        marginBottom: '20px',
    },
    titleInput: {
        width: '100%',
        padding: '10px 0',
        border: 'none',
        borderBottom: '1px solid #ccc',
        fontSize: '16px',
    },
    input: {
        width: '100%',
        padding: '10px',
        borderRadius: '5px',
        border: '1px solid #ccc',
        fontSize: '16px',
    },
    textarea: {
        width: '100%',
        padding: '10px',
        borderRadius: '5px',
        border: '1px solid #ccc',
        fontSize: '16px',
        minHeight: '120px', // Altura mínima para o textarea
        resize: 'none', // Impede o redimensionamento do textarea pelo usuário
        boxSizing: 'border-box', // Garante que o padding e a borda não alterem o tamanho total do elemento
    },
    status: {
        padding: '10px',
        fontSize: '16px',
        backgroundColor: '#f4f4f4',
        borderRadius: '5px',
        border: '1px solid #ccc',
    },
    modalFooter: {
        display: 'flex',
        justifyContent: 'space-between',
    },
    saveButton: {
        padding: '10px 20px',
        border: 'none',
        borderRadius: '5px',
        backgroundColor: '#007bff',
        color: '#fff',
        cursor: 'pointer',
        fontSize: '16px',
    },
    advanceButton: {
        padding: '10px 20px',
        border: 'none',
        borderRadius: '5px',
        backgroundColor: '#28a745',
        color: '#fff',
        cursor: 'pointer',
        fontSize: '16px',
    },
    recedeButton: {
        padding: '10px 20px',
        border: 'none',
        borderRadius: '5px',
        backgroundColor: '#ffc107',
        color: '#fff',
        cursor: 'pointer',
        fontSize: '16px',
    },
    successMessage: {
        position: 'fixed',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        backgroundColor: '#28a745',
        color: '#fff',
        padding: '15px 20px',
        borderRadius: '5px',
        zIndex: '9999',
        textAlign: 'center',
        boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
        display: 'flex',
        alignItems: 'center',
    },
    successIcon: {
        fontSize: '24px',
        marginRight: '10px',
    },
    errorMessage: {
        position: 'fixed',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        backgroundColor: '#dc3545',
        color: '#fff',
        padding: '15px 20px',
        borderRadius: '5px',
        zIndex: '9999',
        textAlign: 'center',
        boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
    },
};

export default EditModal;
