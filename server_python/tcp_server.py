import socket

HOST = '0.0.0.0'
PORT = 5000

def main():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind((HOST, PORT))
    server_socket.listen(1)

    print(f"[TCP Server] Ascult pe portul {PORT}...")
    conn, addr = server_socket.accept()
    print(f"[TCP Server] Conectat cu {addr}")

    try:
        while True:
            data = conn.recv(1024)
            if not data:
                print("[TCP Server] Conexiunea a fost închisă de client.")
                break

            mesaj_primit = data.decode('utf-8').strip()
            print(f"[Client]: {mesaj_primit}")

            if mesaj_primit.lower() == 'exit':
                print("[TCP Server] Clientul a trimis 'exit'. Închid conexiunea.")
                break

            
            raspuns = input("[Tu (Server)]: ")
            conn.sendall((raspuns + "\n").encode('utf-8'))

            if raspuns.strip().lower() == 'exit':
                print("[TCP Server] Ai trimis 'exit'. Închid conexiunea.")
                break

    finally:
        conn.close()
        server_socket.close()
        print("[TCP Server] Socket închis.")

if __name__ == '__main__':
    main()
