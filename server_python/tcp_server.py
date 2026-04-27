import socket

HOST = '0.0.0.0'
PORT = 5000

def main():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind((HOST, PORT))
    server_socket.listen(1)

    print(f"[TCP Server] Listen on port {PORT}...")
    conn, addr = server_socket.accept()
    print(f"[TCP Server] Connected with {addr}")

    try:
        while True:
            # message from client received
            data = conn.recv(1024)
            if not data:
                print("[TCP Server] Connection was closed by client.")
                break

            mesaj_primit = data.decode('utf-8')
            print(f"[Client]: {mesaj_primit}")

            if mesaj_primit.strip().lower() == 'exit':
                print("[TCP Server] Client sent 'exit'. Closing connection.")
                break

            # send answer
            raspuns = input("[You (Server)]: ")
            conn.sendall(raspuns.encode('utf-8'))

            if raspuns.strip().lower() == 'exit':
                print("[TCP Server] You sent 'exit'. Closing connection.")
                break

    finally:
        conn.close()
        server_socket.close()
        print("[TCP Server] Socket closed.")

if __name__ == '__main__':
    main()