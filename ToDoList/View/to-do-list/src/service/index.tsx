import axios, { AxiosInstance } from "axios";
import {TaskType} from "../../types/TaskType";
import {ITask} from "./entity/ITask";
function setHttpServer(): AxiosInstance {
    return axios.create({
        baseURL: "http://localhost:8080/api",
    });
}

export const getTasks = async (): Promise<TaskType[]> => {
    const { data } = await setHttpServer().get(`/task/v1`);
    return data;
};

export const getTasksByStatus = async (status: string): Promise<ITask[]> => {

    const { data } = await setHttpServer().get(`/task/v1/status/${status}`);
    return data;
};
export const postTask = async (
    task : any
): Promise<any> => {
    const { data } = await setHttpServer().post(
        `/task/v1`, task
    );
    return data;
}
export const onAdvanceTask = async (
    id : number,
    status : number
): Promise<any> => {
    const { data } = await setHttpServer().put(
        `/task/v1/id/${id}/status/${status}`);
    return data;
}

export const deleteTasks = async (id : number): Promise<boolean> => {
    try{
        const { data } = await setHttpServer().delete(`/task/v1/${id}`);
        return data;
    }catch (e) {
        return e.response.data;
    }
};