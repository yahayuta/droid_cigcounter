class CigaretteLog {
  final int? id;
  final int timestamp; // Unix timestamp in milliseconds

  CigaretteLog({this.id, required this.timestamp});

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'timestamp': timestamp,
    };
  }

  factory CigaretteLog.fromMap(Map<String, dynamic> map) {
    return CigaretteLog(
      id: map['id'],
      timestamp: map['timestamp'],
    );
  }
}
