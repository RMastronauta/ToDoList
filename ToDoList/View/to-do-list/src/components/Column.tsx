// @ts-ignore
import Card from './Card.tsx';


const Column = ({ task, status }) => {
    return (
        <div style={{ width: '30%', margin: '0 10px', border: '1px solid #ccc', borderRadius: '5px', padding: '10px', backgroundColor: '#f4f5f7' }}>
            <h3 style={styles.h3}>{status}</h3>
            <Card key={task.id} tasks={task} />
        </div>
    );
};

const styles = {
    h3: {
        textAlign: 'center',
        marginTop: '20px',
        color: '#333',
    }
};


export default Column;