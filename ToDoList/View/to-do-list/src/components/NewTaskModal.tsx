import React, { useState } from 'react';
import { FaTimes, FaCheckCircle } from 'react-icons/fa';
import { TaskTypes } from '../enums/taskTypes.tsx';
import { postTask } from '../service/index.tsx';

const NewTaskModal = ({ onClose }) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [taskType, setTaskType] = useState(TaskTypes.DATA);
    const [endDate, setEndDate] = useState('');
    const [days, setDays] = useState('');
    const [priority, setPriority] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    const [showErrorMessage, setShowErrorMessage] = useState(false);

    const handleSaveClick = async () => {
        try {
            setIsLoading(true);

            const data = {
                titulo: title,
                description: description,
                complete: false,
                createdAt: getCurrentDate(),
                dataFim: taskType === TaskTypes.DATA ? endDate : null,
                status: 0,
                taskLivre: taskType === TaskTypes.LIVRE,
                prazo: taskType === TaskTypes.DIAS ? parseInt(days) : null,
                prioridade: getPriorityValue(priority),
                tipoTarefa: getTaskTypeValue(taskType),
            };

            const response = await postTask(data);
            debugger
            if (response.id > 0) {
                setShowSuccessMessage(true);
            } else {
                throw new Error('Erro ao salvar a tarefa');
            }
        } catch (error) {
            setShowErrorMessage(true);
        } finally {
            setTimeout(() => {
                setShowSuccessMessage(false);
                onClose();
                window.location.reload();
            }, 3000);
            setIsLoading(false);
        }
    };

    const getCurrentDate = () => {
        const today = new Date();
        const year = today.getFullYear();
        let month = today.getMonth() + 1;
        let day = today.getDate();

        if (month < 10) {
            month = '0' + month;
        }
        if (day < 10) {
            day = '0' + day;
        }

        return `${year}-${month}-${day}`;
    };

    const getPriorityValue = (priority) => {
        switch (priority) {
            case 'ALTO':
                return 0;
            case 'MÉDIA':
                return 1;
            case 'BAIXO':
                return 2;
            default:
                return null;
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

    const handlePriorityChange = (event) => {
        setPriority(event.target.value);
    };

    return (
        <div style={styles.modalOverlay}>
            <div style={styles.modalContent}>
                <div style={styles.modalHeader}>
                    <h2>Adicionar Nova Tarefa</h2>
                    <FaTimes onClick={onClose} style={styles.closeIcon} />
                </div>
                <div style={styles.modalBody}>
                    <div style={styles.formGroup}>
                        <label>Título</label>
                        <input
                            type="text"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            style={styles.input}
                        />
                    </div>
                    <div style={styles.formGroup}>
                        <label>Descrição</label>
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            style={styles.textarea}
                            rows={4}
                        />
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
                            onChange={handlePriorityChange}
                            style={styles.input}
                        >
                            <option value="">Selecione a Prioridade</option>
                            <option value="ALTO">ALTO</option>
                            <option value="MÉDIA">MÉDIA</option>
                            <option value="BAIXO">BAIXO</option>
                        </select>
                    </div>
                </div>
                <div style={styles.modalFooter}>
                    <button onClick={handleSaveClick} style={styles.saveButton} disabled={isLoading}>
                        {isLoading ? 'Salvando...' : 'Salvar'}
                    </button>
                </div>
            </div>
            {showSuccessMessage && (
                <div style={styles.successMessage}>
                    <FaCheckCircle style={styles.successIcon} />
                    <span>Tarefa criada com sucesso!</span>
                </div>
            )}
            {showErrorMessage && (
                <div style={styles.errorMessage}>
                    Erro ao salvar a tarefa. Por favor, tente novamente.
                </div>
            )}
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
    input: {
        width: '100%',
        padding: '10px',
        borderRadius: '5px',
        border: '1px solid #ccc',
        fontSize: '16px',
        boxSizing: 'border-box', // Garante que o padding e a borda não alterem o tamanho total do elemento
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
    modalFooter: {
        display: 'flex',
        justifyContent: 'flex-end',
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

export default NewTaskModal;
